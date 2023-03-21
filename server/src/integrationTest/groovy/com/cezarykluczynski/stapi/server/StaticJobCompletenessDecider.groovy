package com.cezarykluczynski.stapi.server

import com.cezarykluczynski.stapi.model.step.SimpleStep
import com.google.common.collect.Lists
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.batch.core.BatchStatus
import org.springframework.beans.BeansException
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

class StaticJobCompletenessDecider {

	private static List<SimpleStep> simpleStepList

	@Service
	static class MockJobCompletenessDecider {

		@Inject
		@SuppressWarnings('PrivateFieldCouldBeFinal')
		private EntityManager entityManager

		MockJobCompletenessDecider(EntityManager entityManager) {
			this.entityManager = entityManager
		}

		@Transactional
		List<SimpleStep> getSteps() {
			entityManager
					.createQuery('from SimpleStep')
					.resultList
		}

	}

	static boolean isStepCompleted(String stepName) {
		initializeMockJobCompletenessDecider()

		simpleStepList.stream().anyMatch { step -> step.stepName == stepName && BatchStatus.COMPLETED == step.status }
	}

	static boolean areStepsCompleted(String... stepNames) {
		return stepNames.toList().stream().allMatch { isStepCompleted(it) }
	}

	private static void initializeMockJobCompletenessDecider() {
		if (simpleStepList == null) {
			ConfigurableApplicationContext applicationContext = createApplicationContext()
			try {
				simpleStepList = applicationContext.getBean(MockJobCompletenessDecider).steps
			} catch (BeansException e) {
				simpleStepList = Lists.newArrayList()
			}
			applicationContext.close()
		}
	}

	@SuppressWarnings(['InconsistentPropertySynchronization', 'SynchronizedMethod'])
	private static synchronized ConfigurableApplicationContext createApplicationContext() {
		Application.produceSpringApplicationBuilder()
				.run(
						'--server.port=8543',
						'--spring.profiles.active=default,stapi-custom',
						'--spring.batch.job.enabled=false',
						'--spring.liquibase.enabled=false'
				)
	}

}

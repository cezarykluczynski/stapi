package com.cezarykluczynski.stapi.server

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.model.step.SimpleStep
import com.google.common.collect.Lists
import liquibase.integration.spring.SpringLiquibase
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.beans.BeansException
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.sql.DataSource
import javax.transaction.Transactional

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

	static class AlwaysCompletedStepCompletenessDecider extends StepCompletenessDecider {

		AlwaysCompletedStepCompletenessDecider() {
			super(null, null)
		}

		@Override
		boolean isStepComplete(String jobName, String stepName) {
			true
		}

	}

	@Configuration
	@EnableBatchProcessing
	static class EtlMockConfiguration {

		@Inject
		private ApplicationContext applicationContext

		@Primary
		@Bean
		StepCompletenessDecider jobCompletenessDecider() {
			new AlwaysCompletedStepCompletenessDecider()
		}

		@Bean
		@ConditionalOnProperty(name = 'liquibase.enabled', havingValue = 'false')
		SpringLiquibase liquibase() {
			new SpringLiquibase(
					changeLog: 'classpath:liquibase/changelog.xml',
					dataSource: applicationContext.getBean(DataSource),
					defaultSchema: 'stapi'
			)
		}

	}

	static boolean isStepCompleted(String stepName) {
		initializeMockJobCompletenessDecider()

		simpleStepList.stream().anyMatch { step -> step.stepName == stepName && BatchStatus.COMPLETED == step.status }
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
				.sources(EtlMockConfiguration)
				.run(
						'--server.port=8543',
						'--spring.profiles.active=default,stapi-custom',
						'--etl.enabled=false',
						'--liquibase.enabled=false'
				)
	}

}

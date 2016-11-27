package com.cezarykluczynski.stapi.server

import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.model.step.SimpleStep
import com.cezarykluczynski.stapi.util.constant.SpringProfile
import com.google.common.collect.Lists
import liquibase.spring.SpringLiquibase
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.sql.DataSource
import javax.transaction.Transactional

class StaticJobCompletenessDecider {

	private static ConfigurableApplicationContext applicationContext

	private static List<SimpleStep> simpleStepList

	@Service
	public static class MockJobCompletenessDecider {

		@Inject
		private EntityManager entityManager

		public MockJobCompletenessDecider(EntityManager entityManager) {
			this.entityManager = entityManager
		}

		@Transactional
		public List<SimpleStep> getSteps() {
			return simpleStepList = entityManager
					.createQuery("from SimpleStep")
					.resultList
		}

	}

	public static class AlwaysCompletedJobCompletenessDecider extends JobCompletenessDecider {

		public AlwaysCompletedJobCompletenessDecider() {
			super(null)
		}

		@Override
		public boolean isStepComplete(String stepName) {
			return true
		}

	}

	@Configuration
	@EnableBatchProcessing
	public static class EtlMockConfiguration {

		@Inject
		private ApplicationContext applicationContext

		@Primary
		@Bean
		JobCompletenessDecider jobCompletenessDecider() {
			return new AlwaysCompletedJobCompletenessDecider()
		}

		@Bean
		@Profile(SpringProfile.ETL_NOT)
		public SpringLiquibase liquibase() {
			return new SpringLiquibase(
					changeLog: "classpath:liquibase/changelog.xml",
					dataSource: applicationContext.getBean(DataSource)
			)
		}

	}

	public static boolean isStepCompleted(String stepName) {
		initializeMockJobCompletenessDecider()

		return simpleStepList.stream().anyMatch({
			step -> step.stepName.equals(stepName) && BatchStatus.COMPLETED.equals(step.status)
		})
	}

	private static void initializeMockJobCompletenessDecider() {
		if (simpleStepList == null) {
			ConfigurableApplicationContext applicationContext = getApplicationContext()
			try {
				simpleStepList = applicationContext.getBean(MockJobCompletenessDecider).getSteps()
			} catch (Throwable e) {
				simpleStepList = Lists.newArrayList()
		}
			applicationContext.close()
		}
	}

	private static synchronized ConfigurableApplicationContext getApplicationContext() {
		return applicationContext = Application
				.produceSpringApplicationBuilder()
						.sources(EtlMockConfiguration)
						.run(
								"--server.port=8543",
								"--spring.profiles.active=default,db"
						)
	}

}

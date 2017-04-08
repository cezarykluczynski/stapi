package com.cezarykluczynski.stapi.etl.configuration.job

import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.dao.ExecutionContextDao
import org.springframework.batch.core.repository.dao.JobExecutionDao
import org.springframework.batch.core.repository.dao.JobInstanceDao
import org.springframework.batch.core.repository.dao.StepExecutionDao
import org.springframework.batch.core.repository.support.SimpleJobRepository
import spock.lang.Specification

class SpringBatchDaoConfigurationTest extends Specification {

	private JobInstanceDao jobInstanceDaoMock

	private JobExecutionDao jobExecutionDaoMock

	private StepExecutionDao stepExecutionDaoMock

	private ExecutionContextDao executionContextDaoMock

	private JobRepository jobRepository

	private SpringBatchDaoConfiguration springBatchDaoConfiguration

	void setup() {
		jobInstanceDaoMock = Mock()
		jobExecutionDaoMock = Mock()
		stepExecutionDaoMock = Mock()
		executionContextDaoMock = Mock()
		jobRepository = new SimpleJobRepository(jobInstanceDaoMock, jobExecutionDaoMock, stepExecutionDaoMock, executionContextDaoMock)
		springBatchDaoConfiguration = new SpringBatchDaoConfiguration(jobRepository: jobRepository)
	}

	void "gets JobRepository dependencies"() {
		expect:
		springBatchDaoConfiguration.jobInstanceDao() == jobInstanceDaoMock
		springBatchDaoConfiguration.jobExecutionDao() == jobExecutionDaoMock
		springBatchDaoConfiguration.stepExecutionDao() == stepExecutionDaoMock
		springBatchDaoConfiguration.executionContextDao() == executionContextDaoMock
	}

}

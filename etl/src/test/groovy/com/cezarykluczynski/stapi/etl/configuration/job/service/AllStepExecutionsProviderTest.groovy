package com.cezarykluczynski.stapi.etl.configuration.job.service

import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.google.common.collect.Lists
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.repository.dao.ExecutionContextDao
import org.springframework.batch.core.repository.dao.JobExecutionDao
import org.springframework.batch.core.repository.dao.JobInstanceDao
import org.springframework.batch.core.repository.dao.StepExecutionDao
import org.springframework.batch.item.ExecutionContext
import spock.lang.Specification

class AllStepExecutionsProviderTest extends Specification {

	private JobInstanceDao jobInstanceDaoMock

	private JobExecutionDao jobExecutionDaoMock

	private StepExecutionDao stepExecutionDaoMock

	private ExecutionContextDao executionContextDaoMock

	private AllStepExecutionsProvider allStepExecutionsProvider

	void setup() {
		jobInstanceDaoMock = Mock()
		jobExecutionDaoMock = Mock()
		stepExecutionDaoMock = Mock()
		executionContextDaoMock = Mock()
		allStepExecutionsProvider = new AllStepExecutionsProvider(jobInstanceDaoMock, jobExecutionDaoMock,
				stepExecutionDaoMock, executionContextDaoMock)
	}

	void "returns empty list when no job instance can be found"() {
		when:
		List<StepExecution> stepExecutionList = allStepExecutionsProvider.provide(JobName.JOB_CREATE)

		then:
		1 * jobInstanceDaoMock.getJobInstance(JobName.JOB_CREATE, _ as JobParameters) >> null
		0 * _
		stepExecutionList.empty
	}

	void "returns empty list when no JobExecution can be found"() {
		given:
		JobInstance jobInstance = Mock()

		when:
		List<StepExecution> stepExecutionList = allStepExecutionsProvider.provide(JobName.JOB_CREATE)

		then:
		1 * jobInstanceDaoMock.getJobInstance(JobName.JOB_CREATE, _ as JobParameters) >> jobInstance
		1 * jobExecutionDaoMock.findJobExecutions(jobInstance) >> Lists.newArrayList()
		0 * _
		stepExecutionList.empty
	}

	void "returns step executions from all job executions"() {
		given:
		JobExecution jobExecution1 = Mock()
		JobExecution jobExecution2 = Mock()
		ExecutionContext executionContext1 = Mock()
		ExecutionContext executionContext2 = Mock()
		StepExecution job1stepExecution1 = Mock()
		StepExecution job1stepExecution2 = Mock()
		StepExecution job2stepExecution1 = Mock()
		StepExecution job2stepExecution2 = Mock()
		JobInstance jobInstance = Mock()

		when:
		List<StepExecution> stepExecutionList = allStepExecutionsProvider.provide(JobName.JOB_CREATE)

		then:
		1 * jobInstanceDaoMock.getJobInstance(JobName.JOB_CREATE, _ as JobParameters) >> jobInstance
		1 * jobExecutionDaoMock.findJobExecutions(jobInstance) >> Lists.newArrayList(jobExecution1, jobExecution2)
		1 * executionContextDaoMock.getExecutionContext(jobExecution1) >> executionContext1
		1 * jobExecution1.setExecutionContext(executionContext1)
		1 * stepExecutionDaoMock.addStepExecutions(jobExecution1)
		1 * jobExecution1.stepExecutions >> Lists.newArrayList(job1stepExecution1, job1stepExecution2)
		1 * executionContextDaoMock.getExecutionContext(jobExecution2) >> executionContext2
		1 * jobExecution2.setExecutionContext(executionContext2)
		1 * stepExecutionDaoMock.addStepExecutions(jobExecution2)
		1 * jobExecution2.stepExecutions >> Lists.newArrayList(job2stepExecution1, job2stepExecution2)
		0 * _
		stepExecutionList.size() == 4
		stepExecutionList.contains job1stepExecution1
		stepExecutionList.contains job1stepExecution2
		stepExecutionList.contains job2stepExecution1
		stepExecutionList.contains job2stepExecution2
	}

}

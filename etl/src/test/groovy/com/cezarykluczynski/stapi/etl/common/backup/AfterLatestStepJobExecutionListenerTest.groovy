package com.cezarykluczynski.stapi.etl.common.backup

import com.cezarykluczynski.stapi.etl.configuration.job.AsyncAfterJobExecutor
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class AfterLatestStepJobExecutionListenerTest extends Specification {

	static final Long JOB_ID = 11L
	static final Long STEP_ID_1 = 21L
	static final Long STEP_ID_2 = 22L
	static final String STEP_NAME_1 = 'STEP_NAME_1'
	static final String STEP_NAME_2 = 'STEP_NAME_2'

	AsyncAfterJobExecutor asyncAfterJobExecutorMock

	AfterLatestStepJobExecutionListener afterLatestStepJobExecutionListener

	void setup() {
		asyncAfterJobExecutorMock = Mock()
		afterLatestStepJobExecutionListener = new AfterLatestStepJobExecutionListener(asyncAfterJobExecutorMock)
	}

	void "executes step for latest step"() {
		given:
		JobExecution jobExecution = new JobExecution(JOB_ID, new JobParameters())
		StepExecution stepExecution1 = new StepExecution(STEP_NAME_1, jobExecution, STEP_ID_1)
		StepExecution stepExecution2 = new StepExecution(STEP_NAME_2, jobExecution, STEP_ID_2)
		stepExecution1.setExitStatus(ExitStatus.COMPLETED)
		stepExecution2.setExitStatus(ExitStatus.COMPLETED)
		jobExecution.addStepExecutions([stepExecution1, stepExecution2])

		when:
		afterLatestStepJobExecutionListener.afterJob(jobExecution)

		then:
		1 * asyncAfterJobExecutorMock.execute(stepExecution2)
		0 * _
	}

	void "does nothing for an empty execution"() {
		given:
		JobExecution jobExecution = new JobExecution(JOB_ID, new JobParameters())

		when:
		afterLatestStepJobExecutionListener.afterJob(jobExecution)

		then:
		0 * _
	}

}

package com.cezarykluczynski.stapi.etl.common.backup

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.repository.dao.JobExecutionDao
import org.springframework.batch.core.repository.dao.JobInstanceDao
import spock.lang.Specification

class BackupAfterStepConsistencyGuaranteeTest extends Specification {

	static final Long JOB_1_ID = 11L
	static final Long JOB_2_ID = 12L
	static final String JOB_1_NAME = 'JOB_1_NAME'
	static final String JOB_2_NAME = 'JOB_2_NAME'
	static final String JOB_2_STEP_1_NAME = 'JOB_2_STEP_1_NAME'
	static final String JOB_2_STEP_2_NAME = 'JOB_2_STEP_2_NAME'

	JobExecutionDao jobExecutionDaoMock
	JobInstanceDao jobInstanceDaoMock
	BackupAfterStepConsistencyGuarantee backupAfterStepConsistencyGuarantee

	void setup() {
		jobExecutionDaoMock = Mock()
		jobInstanceDaoMock = Mock()
		backupAfterStepConsistencyGuarantee = new BackupAfterStepConsistencyGuarantee(jobExecutionDaoMock, jobInstanceDaoMock)
	}

	void "unblocks thread when jobs and steps are completed"() {
		given:
		JobExecution jobExecutionCompleted = new JobExecution(JOB_1_ID)
		JobExecution jobExecutionNotCompleted = new JobExecution(JOB_2_ID)
		JobExecution jobExecutionCompletedWithNotCompletedSteps = new JobExecution(JOB_2_ID)
		JobExecution jobExecutionCompletedWithCompletedSteps = new JobExecution(JOB_2_ID)
		StepExecution stepExecution = new StepExecution(JOB_2_STEP_1_NAME, null)
		StepExecution stepExecutionNotCompleted = new StepExecution(JOB_2_STEP_2_NAME, null)
		StepExecution stepExecutionCompleted = new StepExecution(JOB_2_STEP_2_NAME, null)
		stepExecution.setExitStatus(ExitStatus.COMPLETED)
		stepExecutionNotCompleted.setExitStatus(ExitStatus.EXECUTING)
		stepExecutionCompleted.setExitStatus(ExitStatus.COMPLETED)
		jobExecutionCompleted.setExitStatus(ExitStatus.COMPLETED)
		jobExecutionNotCompleted.setExitStatus(ExitStatus.EXECUTING)
		jobExecutionCompletedWithNotCompletedSteps.setExitStatus(ExitStatus.COMPLETED)
		jobExecutionCompletedWithNotCompletedSteps.addStepExecutions([stepExecution, stepExecutionNotCompleted])
		jobExecutionCompletedWithCompletedSteps.setExitStatus(ExitStatus.COMPLETED)
		jobExecutionCompletedWithCompletedSteps.addStepExecutions([stepExecution, stepExecutionCompleted])

		when:
		backupAfterStepConsistencyGuarantee.ensureState()

		then:
		1 * jobInstanceDaoMock.jobNames >> [JOB_1_NAME, JOB_2_NAME]
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_1_NAME) >> Set.of(jobExecutionCompleted)
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_2_NAME) >> Set.of(jobExecutionNotCompleted)
		0 * _

		then:
		1 * jobInstanceDaoMock.jobNames >> [JOB_1_NAME, JOB_2_NAME]
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_1_NAME) >> Set.of(jobExecutionCompleted)
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_2_NAME) >> Set.of(jobExecutionCompletedWithNotCompletedSteps)
		0 * _

		then:
		1 * jobInstanceDaoMock.jobNames >> [JOB_1_NAME, JOB_2_NAME]
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_1_NAME) >> Set.of(jobExecutionCompleted)
		1 * jobExecutionDaoMock.findRunningJobExecutions(JOB_2_NAME) >> Set.of(jobExecutionCompletedWithCompletedSteps)
		0 * _
	}

}

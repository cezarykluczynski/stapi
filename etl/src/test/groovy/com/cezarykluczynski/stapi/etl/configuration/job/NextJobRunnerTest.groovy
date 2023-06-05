package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.success.PreviousStepSuccessValidator
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.launch.JobLauncher
import spock.lang.Specification

class NextJobRunnerTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'

	private JobBuilder jobBuilderMock
	private JobLauncher jobLauncherMock
	private PreviousStepSuccessValidator previousStepSuccessValidatorMock
	private NextJobRunner nextJobRunner

	void setup() {
		jobBuilderMock = Mock()
		jobLauncherMock = Mock()
		previousStepSuccessValidatorMock = Mock()
		nextJobRunner = new NextJobRunner(jobBuilderMock, jobLauncherMock, previousStepSuccessValidatorMock)
	}

	void "does not schedule next job when previous step was not completed"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME, null)
		stepExecution.setExitStatus(ExitStatus.FAILED)

		when:
		nextJobRunner.scheduleNext(stepExecution)

		then:
		0 * _
	}

	void "does not schedule next job when previous job was not completed"() {
		given:
		JobInstance jobInstance = new JobInstance(1L, 'jobName')
		JobExecution jobExecution = new JobExecution(jobInstance, null, null)
		StepExecution stepExecution = new StepExecution(STEP_NAME, jobExecution)
		stepExecution.setExitStatus(ExitStatus.COMPLETED)
		jobExecution.setExitStatus(ExitStatus.FAILED)

		when:
		nextJobRunner.scheduleNext(stepExecution)

		then:
		0 * _
	}

	void "does not schedule next job when previous step was not successful"() {
		given:
		JobExecution jobExecution = new JobExecution(null, null, null)
		StepExecution stepExecution = new StepExecution(STEP_NAME, jobExecution)
		stepExecution.setExitStatus(ExitStatus.COMPLETED)
		jobExecution.setExitStatus(ExitStatus.COMPLETED)

		when:
		nextJobRunner.scheduleNext(stepExecution)

		then:
		1 * previousStepSuccessValidatorMock.wasSuccessful(stepExecution) >> false
		0 * _
	}

	void "does not schedule next job when previous step was successful, but there is no next step available"() {
		given:
		JobExecution jobExecution = new JobExecution(null, null, null)
		StepExecution stepExecution = new StepExecution(STEP_NAME, jobExecution)
		stepExecution.setExitStatus(ExitStatus.COMPLETED)
		jobExecution.setExitStatus(ExitStatus.COMPLETED)

		when:
		nextJobRunner.scheduleNext(stepExecution)

		then:
		1 * previousStepSuccessValidatorMock.wasSuccessful(stepExecution) >> true
		1 * jobBuilderMock.buildNext() >> null
		0 * _
	}

	void "schedules next job when previous step was successful, and next job is available"() {
		given:
		JobParameters jobParameters = Mock()
		JobExecution jobExecution = new JobExecution(null, null, jobParameters)
		StepExecution stepExecution = new StepExecution(STEP_NAME, jobExecution)
		stepExecution.setExitStatus(ExitStatus.COMPLETED)
		jobExecution.setExitStatus(ExitStatus.COMPLETED)
		Job job = Mock()

		when:
		nextJobRunner.scheduleNext(stepExecution)

		then:
		1 * previousStepSuccessValidatorMock.wasSuccessful(stepExecution) >> true
		1 * jobBuilderMock.buildNext() >> job
		1 * jobLauncherMock.run(job, jobParameters)
		0 * _
	}

}

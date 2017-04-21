package com.cezarykluczynski.stapi.etl.configuration.job.service

import com.cezarykluczynski.stapi.etl.configuration.job.StepConfigurationValidator
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.google.common.collect.Lists
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class JobCompletenessDeciderTest extends Specification {

	private static final String JOB_NAME = JobName.JOB_CREATE
	private static final Integer VALID_NUMBER_OF_STEPS = (int) StepConfigurationValidator.NUMBER_OF_STEPS
	private static final Integer INVALID_NUMBER_OF_STEPS = VALID_NUMBER_OF_STEPS - 1

	private AllStepExecutionsProvider allStepExecutionsProviderMock

	private JobCompletenessDecider jobCompletenessDecider

	void setup() {
		allStepExecutionsProviderMock = Mock()
		jobCompletenessDecider = new JobCompletenessDecider(allStepExecutionsProviderMock)
	}

	void "returns true when all step executions in step are completed and number of executions equals number of steps"() {
		given:
		List<StepExecution> stepExecutionList = createStepExecutionList(VALID_NUMBER_OF_STEPS)

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		completed
	}

	void "returns false when number of executions does not equal number of steps"() {
		given:
		List<StepExecution> stepExecutionList = createStepExecutionList(INVALID_NUMBER_OF_STEPS)

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		!completed
	}

	void "returns false when number of executions equals number of steps, but not all step executions are completed"() {
		given:
		StepExecution stepExecution = Mock()
		List<StepExecution> stepExecutionList = createStepExecutionList(INVALID_NUMBER_OF_STEPS)
		stepExecutionList.add(stepExecution)

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		1 * stepExecution.status >> BatchStatus.UNKNOWN
		!completed
	}

	private List<StepExecution> createStepExecutionList(int numberOfMocks) {
		List<StepExecution> stepExecutionList = Lists.newArrayList()

		int i = 0
		while (numberOfMocks > i) {
			i++

			StepExecution stepExecution = Mock()
			stepExecution.status >> BatchStatus.COMPLETED
			stepExecutionList.add(stepExecution)
		}

		stepExecutionList
	}

}

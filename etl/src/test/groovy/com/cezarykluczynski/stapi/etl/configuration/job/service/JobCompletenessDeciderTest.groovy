package com.cezarykluczynski.stapi.etl.configuration.job.service

import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.google.common.collect.Lists
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class JobCompletenessDeciderTest extends Specification {

	private static final String JOB_NAME = JobName.JOB_CREATE

	private AllStepExecutionsProvider allStepExecutionsProviderMock

	private JobCompletenessDecider jobCompletenessDecider

	def setup() {
		allStepExecutionsProviderMock = Mock(AllStepExecutionsProvider)
		jobCompletenessDecider = new JobCompletenessDecider(allStepExecutionsProviderMock)
	}

	def "returns true when all step executions in step are completed and number of executions equals number of steps"() {
		given:
		List<StepExecution> stepExecutionList = createStepExecutionList(5)

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		completed
	}

	def "returns false when number of executions does not equal number of steps"() {
		given:
		List<StepExecution> stepExecutionList = createStepExecutionList(4)

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		!completed
	}

	def "returns false when number of executions equals number of steps, but not all step executions are completed"() {
		given:
		List<StepExecution> stepExecutionList = createStepExecutionList(4)
		stepExecutionList.add(Mock(StepExecution) {
			getStatus() >> BatchStatus.UNKNOWN
		})

		when:
		boolean completed = jobCompletenessDecider.isJobCompleted(JOB_NAME)

		then:
		1 * allStepExecutionsProviderMock.provide(JOB_NAME) >> stepExecutionList
		!completed
	}

	private List<StepExecution> createStepExecutionList(int numberOfMocks) {
		List<StepExecution> stepExecutionList = Lists.newArrayList()

		int i = 0;
		while (numberOfMocks > i) {
			i++

			stepExecutionList.add(Mock(StepExecution) {
				getStatus() >> BatchStatus.COMPLETED
			})
		}

		return stepExecutionList
	}


}

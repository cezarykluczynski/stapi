package com.cezarykluczynski.stapi.etl.configuration.job.success

import com.cezarykluczynski.stapi.etl.common.service.step.diff.StepDiff
import com.cezarykluczynski.stapi.etl.common.service.step.diff.StepDiffProvider
import com.google.common.collect.Lists
import org.springframework.batch.core.StepExecution
import spock.lang.Specification
import spock.lang.Unroll

class PreviousStepSuccessValidatorTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'

	private StepSuccessParametersProvider stepSuccessParametersProviderMock
	private StepDiffProvider stepDiffProviderMock
	private PreviousStepSuccessValidator previousStepSuccessValidator

	void setup() {
		stepSuccessParametersProviderMock = Mock()
		stepDiffProviderMock = Mock()
		previousStepSuccessValidator = new PreviousStepSuccessValidator(stepSuccessParametersProviderMock, stepDiffProviderMock)
	}

	@Unroll('when step has diff #stepDiff and parameters #stepSuccessParameters, #result is returned')
	void "returns results of step success validation"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME, null)
		stepDiffProviderMock.provide(stepExecution) >> stepDiff
		stepSuccessParametersProviderMock.getStepSuccessParameters(STEP_NAME) >> stepSuccessParameters

		expect:
		previousStepSuccessValidator.wasSuccessful(stepExecution) == result

		where:
		stepDiff                   | stepSuccessParameters                | result
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 5, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 6, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(2, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(3, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(2, null, 5, null)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(3, null, 6, null)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 5, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(1, null, 6, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(2, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(3, null, 4, null)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(2, null, 5, null)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(3, null, 6, null)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(2, 0, 5, 0)             | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(3, 0, 6, 0)             | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 5)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 6)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 2, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 3, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 2, null, 5)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 3, null, 6)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 5)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 1, null, 6)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 2, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 3, null, 4)       | false
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 2, null, 5)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 3, null, 6)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 2, null, 5)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, 3, null, 6)       | true
		stepDiffOf(2, 5, 100, 103) | parametersOf(null, null, null, null) | true
	}

	private static StepDiff stepDiffOf(int uniquePreviousNamesSize, int uniqueCurrentNamesSize, int previousNamesSize, int currentNamesSize) {
		new StepDiff(STEP_NAME, listOfSize(uniquePreviousNamesSize), listOfSize(uniqueCurrentNamesSize), previousNamesSize, currentNamesSize)
	}

	private static StepSuccessParameters parametersOf(Integer maxEntriesRemoved, Double maxEntriesRemovedPercentage, Integer maxEntriesAdded,
			Double maxEntriesAddedPercentage) {
		StepSuccessParameters.builder()
				.maxEntriesRemoved(maxEntriesRemoved)
				.maxEntriesRemovedPercentage(maxEntriesRemovedPercentage)
				.maxEntriesAdded(maxEntriesAdded)
				.maxEntriesAddedPercentage(maxEntriesAddedPercentage)
				.build()
	}

	private static List<String> listOfSize(int size) {
		List<String> list = Lists.newArrayList()
		for (int i = 0; i < size; i++) {
			list.add UUID.randomUUID().toString()
		}
		list
	}

}

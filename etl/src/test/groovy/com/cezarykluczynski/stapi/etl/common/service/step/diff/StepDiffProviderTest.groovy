package com.cezarykluczynski.stapi.etl.common.service.step.diff

import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class StepDiffProviderTest extends Specification {

	static final Integer PORT = 8687
	static final String STEP_NAME = 'STEP_NAME'

	StepDiffProperties stepDiffProperties
	StepDiffAllNamesProvider stepDiffAllNamesProvider
	StepDiffProvider stepDiffProvider

	void setup() {
		stepDiffProperties = Mock()
		stepDiffAllNamesProvider = Mock()
		stepDiffProvider = new StepDiffProvider(new StepDiffProperties(), stepDiffAllNamesProvider, PORT)
	}

	void "step diff is provided"() {
		given:
		StepExecution stepExecution = new StepExecution(STEP_NAME, null)
		List<String> previousNames = List.of('A', 'B', 'C', 'D')
		List<String> currentNames = List.of('C', 'D', 'E', 'F')

		when:
		StepDiff stepDiff = stepDiffProvider.provide(stepExecution)

		then:
		1 * stepDiffAllNamesProvider.get(STEP_NAME, _) >> previousNames
		1 * stepDiffAllNamesProvider.get(STEP_NAME, _) >> currentNames
		0 * _
		stepDiff.stepName == STEP_NAME
		stepDiff.uniquePreviousNames == ['A', 'B']
		stepDiff.uniqueCurrentNames == ['E', 'F']
	}

}

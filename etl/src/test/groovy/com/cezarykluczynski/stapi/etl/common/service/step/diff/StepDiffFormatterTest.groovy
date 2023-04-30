package com.cezarykluczynski.stapi.etl.common.service.step.diff

import spock.lang.Specification

class StepDiffFormatterTest extends Specification {

	static final String STEP_NAME = 'CREATE_CHARACTERS'

	StepDiffFormatter stepDiffFormatter

	void setup() {
		stepDiffFormatter = new StepDiffFormatter()
	}

	void "formats diff for empty results"() {
		given:
		StepDiff stepDiff = new StepDiff(STEP_NAME, List.of(), List.of())

		when:
		String result = stepDiffFormatter.format(stepDiff)

		then:
		result == "No differences found for step $STEP_NAME."
	}

	void "formats diff for present results"() {
		given:
		List<String> uniquePreviousNames = List.of(
				'Altan Inigo Soong',
				'Bradward Boimler',
				'Bradward Boimler',
				'Samanthan Rutherford',
				'Samanthan Rutherford'
		)
		List<String> uniqueCurrentNames = List.of(
				'Altan Soong',
				'Bernardin',
				'Brad Boimler',
				'Brad Boimler',
				'Daystrom Android M-5-10',
				'Knicknac',
				'Meena Vesper',
				'Roll',
				'Sam Rutherford',
				'Sam Rutherford',
				'Sylvo Toussant'
		)
		StepDiff stepDiff = new StepDiff(STEP_NAME, uniquePreviousNames, uniqueCurrentNames)

		when:
		String result = stepDiffFormatter.format(stepDiff)

		then:
		result == """
Differences for step $STEP_NAME
Previous version unique values: | Current version unique values:
Altan Inigo Soong               | Altan Soong
Bradward Boimler                | Bernardin
Bradward Boimler                | Brad Boimler
Samanthan Rutherford            | Brad Boimler
Samanthan Rutherford            | Daystrom Android M-5-10
                                | Knicknac
                                | Meena Vesper
                                | Roll
                                | Sam Rutherford
                                | Sam Rutherford
                                | Sylvo Toussant"""
	}

}

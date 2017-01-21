package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import spock.lang.Specification
import spock.lang.Unroll

class MaritalStatusProcessorTest extends Specification {

	private MaritalStatusProcessor maritalStatusProcessor

	void setup() {
		maritalStatusProcessor = new MaritalStatusProcessor()
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		maritalStatusProcessor.process(input) == output

		where:
		input                                 | output
		MaritalStatusProcessor.SINGLE         | MaritalStatus.SINGLE
		MaritalStatusProcessor.ANNULLED       | MaritalStatus.SINGLE
		MaritalStatusProcessor.WIDOW          | MaritalStatus.WIDOWED
		MaritalStatusProcessor.ENGAGED        | MaritalStatus.ENGAGED
		MaritalStatusProcessor.DIVORCED       | MaritalStatus.DIVORCED
		MaritalStatusProcessor.SEPARATED      | MaritalStatus.SEPARATED
		MaritalStatusProcessor.REMARRIED      | MaritalStatus.REMARRIED
		MaritalStatusProcessor.MARRIED        | MaritalStatus.MARRIED
		MaritalStatusProcessor.BONDED         | MaritalStatus.MARRIED
		MaritalStatusProcessor.CAPTAINS_WOMAN | MaritalStatus.CAPTAINS_WOMAN
		''                                    | null
		null                                  | null
	}

}

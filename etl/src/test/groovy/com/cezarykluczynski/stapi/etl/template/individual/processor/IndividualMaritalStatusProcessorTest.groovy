package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import spock.lang.Specification
import spock.lang.Unroll

class IndividualMaritalStatusProcessorTest extends Specification {

	private IndividualMaritalStatusProcessor individualMaritalStatusProcessor

	def setup() {
		individualMaritalStatusProcessor = new IndividualMaritalStatusProcessor()
	}

	@Unroll("#output is returned when #input is passed")
	def "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualMaritalStatusProcessor.process(input) == output

		where:
		input                                           | output
		IndividualMaritalStatusProcessor.SINGLE         | MaritalStatus.SINGLE
		IndividualMaritalStatusProcessor.ANNULLED       | MaritalStatus.SINGLE
		IndividualMaritalStatusProcessor.WIDOW          | MaritalStatus.WIDOWED
		IndividualMaritalStatusProcessor.ENGAGED        | MaritalStatus.ENGAGED
		IndividualMaritalStatusProcessor.DIVORCED       | MaritalStatus.DIVORCED
		IndividualMaritalStatusProcessor.SEPARATED      | MaritalStatus.SEPARATED
		IndividualMaritalStatusProcessor.REMARRIED      | MaritalStatus.REMARRIED
		IndividualMaritalStatusProcessor.MARRIED        | MaritalStatus.MARRIED
		IndividualMaritalStatusProcessor.BONDED         | MaritalStatus.MARRIED
		IndividualMaritalStatusProcessor.CAPTAINS_WOMAN | MaritalStatus.CAPTAINS_WOMAN
		""                                              | null
		null                                            | null
	}

}

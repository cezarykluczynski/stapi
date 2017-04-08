package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectTypeProcessorTest extends Specification {

	private static final String VALUE_TO_PROCESS = 'VALUE_TO_PROCESS'

	private AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessorMock

	private AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor

	void setup() {
		astronomicalObjectWikitextProcessorMock = Mock()
		astronomicalObjectTypeProcessor = new AstronomicalObjectTypeProcessor(astronomicalObjectWikitextProcessorMock)
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string input is passed, AstronomicalObjectType is returned"() {
		given:
		astronomicalObjectWikitextProcessorMock.process(VALUE_TO_PROCESS) >> AstronomicalObjectType.CLUSTER

		expect:
		output == astronomicalObjectTypeProcessor.process(input)

		where:
		input                                      | output
		null                                       | null
		AstronomicalObjectTypeProcessor.ARTIFICIAL | AstronomicalObjectType.ARTIFICIAL_PLANET
		AstronomicalObjectTypeProcessor.ASTEROID   | AstronomicalObjectType.ASTEROID
		AstronomicalObjectTypeProcessor.M          | AstronomicalObjectType.M_CLASS_PLANET
		VALUE_TO_PROCESS                           | AstronomicalObjectType.CLUSTER
	}

}

package com.cezarykluczynski.stapi.etl.template.common.processor

import spock.lang.Specification

class ProductionSerialNumberProcessorTest extends Specification {

	private static final String PRODUCTION_SERIAL_NUMBER = '40511-721'
	private static final String PRODUCTION_SERIAL_NUMBER_WITH_SPACES = '40511-721 A B'
	private static final String PRODUCTION_SERIAL_NUMBER_WITH_MULTIPLE_LINES = '''40511-721
A
B
'''
	private static final String PRODUCTION_SERIAL_NUMBER_JSON_FORMATTED =
			'{"comment":"<!-- Extra data production numbers = 401-402-->","content":"' + PRODUCTION_SERIAL_NUMBER + '"}'
	private static final String PRODUCTION_SERIAL_TOO_LONG_NUMBER = 'Too long too long too long too long too long'

	private ProductionSerialNumberProcessor serialNumberProcessor

	void setup() {
		serialNumberProcessor = new ProductionSerialNumberProcessor()
	}

	void "extracts production serial number that is not too long to be processed"() {
		when:
		String productionSerialNumber = serialNumberProcessor.process(PRODUCTION_SERIAL_NUMBER)

		then:
		productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	void "extracts production serial number that is not too long to be processed, excluding everything before space"() {
		when:
		String productionSerialNumber = serialNumberProcessor.process(PRODUCTION_SERIAL_NUMBER_WITH_SPACES)

		then:
		productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	void "extracts production serial number that is not too long to be processed, excluding everything before new line"() {
		when:
		String productionSerialNumber = serialNumberProcessor.process(PRODUCTION_SERIAL_NUMBER_WITH_MULTIPLE_LINES)

		then:
		productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	void "tolerates JSON-formatted production serial number"() {
		when:
		String productionSerialNumber = serialNumberProcessor.process(PRODUCTION_SERIAL_NUMBER_JSON_FORMATTED)

		then:
		productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	void "tolerates too long production serial number"() {
		when:
		String productionSerialNumber = serialNumberProcessor.process(PRODUCTION_SERIAL_TOO_LONG_NUMBER)

		then:
		productionSerialNumber == null
	}

}

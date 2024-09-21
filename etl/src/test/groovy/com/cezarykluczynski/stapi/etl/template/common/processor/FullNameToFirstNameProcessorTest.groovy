package com.cezarykluczynski.stapi.etl.template.common.processor

import spock.lang.Specification

class FullNameToFirstNameProcessorTest extends Specification {

	private FullNameToFirstNameProcessor fullNameToFirstNameProcessor

	void setup() {
		fullNameToFirstNameProcessor = new FullNameToFirstNameProcessor()
	}

	void "gets only word"() {
		when:
		String firstName = fullNameToFirstNameProcessor.process('Mark')

		then:
		firstName == 'Mark'
	}

	void "gets first word of two word string"() {
		when:
		String firstName = fullNameToFirstNameProcessor.process('Patrick Stewart')

		then:
		firstName == 'Patrick'
	}

}

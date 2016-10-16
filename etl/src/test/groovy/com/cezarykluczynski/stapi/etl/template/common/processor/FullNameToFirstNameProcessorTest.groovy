package com.cezarykluczynski.stapi.etl.template.common.processor

import spock.lang.Specification

class FullNameToFirstNameProcessorTest extends Specification {

	private FullNameToFirstNameProcessor fullNameToFirstNameProcessor

	def setup() {
		fullNameToFirstNameProcessor = new FullNameToFirstNameProcessor()
	}

	def "gets only word"() {
		when:
		String firstName = fullNameToFirstNameProcessor.process('Mark')

		then:
		firstName == 'Mark'
	}

	def "gets first word of two word string"() {
		when:
		String firstName = fullNameToFirstNameProcessor.process('Patrick Stewart')

		then:
		firstName == 'Patrick'
	}

	def "gets second word if first word is an initial"() {
		when:
		String firstName = fullNameToFirstNameProcessor.process('J. John Doe')

		then:
		firstName == 'John'
	}

}
package com.cezarykluczynski.stapi.server.common.converter

import spock.lang.Specification

import java.time.DateTimeException
import java.time.LocalDate

class LocalDateRestParamConverterTest extends Specification {

	private LocalDateRestParamConverter localDateRestParamConverter

	def setup() {
		localDateRestParamConverter = new LocalDateRestParamConverter()
	}

	def "converts null to null"() {
		expect:
		localDateRestParamConverter.fromString(null) == null
	}

	def "converts valid date string to LocalDate"() {
		expect:
		localDateRestParamConverter.fromString("1990-02-04") == LocalDate.of(1990, 2, 4)
		localDateRestParamConverter.fromString("1820-03-05") == LocalDate.of(1820, 3, 5)
	}

	def "converts string that does not have two hypens to null"() {
		expect:
		localDateRestParamConverter.fromString("19900204") == null
	}

	def "throws exception for invalid date"() {
		when:
		localDateRestParamConverter.fromString("1990-02-AC")

		then:
		thrown(NumberFormatException)

		when:
		localDateRestParamConverter.fromString("01-02-2003")

		then:
		thrown(DateTimeException)
	}

	def "to string converter returns null"() {
		expect:
		localDateRestParamConverter.toString(LocalDate.MIN) == null
		localDateRestParamConverter.toString(null) == null
	}

}

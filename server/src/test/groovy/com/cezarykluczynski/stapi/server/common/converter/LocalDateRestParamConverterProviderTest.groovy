package com.cezarykluczynski.stapi.server.common.converter

import spock.lang.Specification

import javax.ws.rs.ext.ParamConverter
import java.lang.annotation.Annotation
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateRestParamConverterProviderTest extends Specification {

	private LocalDateRestParamConverterProvider localDateRestParamConverterProvider

	def setup() {
		localDateRestParamConverterProvider = new LocalDateRestParamConverterProvider()
	}


	def "provides LocalDateRestParamConverter"() {
		when:
		ParamConverter converter = localDateRestParamConverterProvider.getConverter(LocalDate, Mock(Type), new Annotation[0])

		then:
		converter instanceof LocalDateRestParamConverter
	}

	def "provides null for other type"() {
		when:
		ParamConverter converter = localDateRestParamConverterProvider.getConverter(LocalDateTime, Mock(Type), new Annotation[0])

		then:
		converter == null
	}

}

package com.cezarykluczynski.stapi.server.common.converter

import spock.lang.Specification

import javax.ws.rs.ext.ParamConverter
import java.lang.annotation.Annotation
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateRestParamConverterProviderTest extends Specification {

	private LocalDateRestParamConverterProvider localDateRestParamConverterProvider

	void setup() {
		localDateRestParamConverterProvider = new LocalDateRestParamConverterProvider()
	}

	void "provides LocalDateRestParamConverter"() {
		when:
		ParamConverter converter = localDateRestParamConverterProvider.getConverter(LocalDate, Mock(Type), new Annotation[0])

		then:
		converter instanceof LocalDateRestParamConverter
	}

	void "provides null for other type"() {
		when:
		ParamConverter converter = localDateRestParamConverterProvider.getConverter(LocalDateTime, Mock(Type), new Annotation[0])

		then:
		converter == null
	}

}

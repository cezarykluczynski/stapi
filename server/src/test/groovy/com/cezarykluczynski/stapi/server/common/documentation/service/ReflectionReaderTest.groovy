package com.cezarykluczynski.stapi.server.common.documentation.service

import org.reflections.Reflections
import spock.lang.Specification

class ReflectionReaderTest extends Specification {

	private ReflectionReader reflectionReader

	void setup() {
		reflectionReader = new ReflectionReader()
	}

	void "reads package"() {
		when:
		Reflections reflections = reflectionReader.readPackage('com.cezarykluczynski.stapi.server.common.documentation.service')

		then:
		reflections.getSubTypesOf(Specification).contains(ReflectionReaderTest)
	}

}

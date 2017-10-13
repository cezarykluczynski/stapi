package com.cezarykluczynski.stapi.server.common

import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import spock.lang.Specification

import javax.ws.rs.FormParam
import java.lang.reflect.Field
import java.util.stream.Collectors

class RestBeanParamsReflectionTest extends Specification {

	void "no RestBeanParams class can have @FormParam annotation on it's uid field"() {
		given:
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage('com.cezarykluczynski.stapi.server'))
				.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()))

		when:
		Set<Field> formParams = reflections.getFieldsAnnotatedWith(FormParam).stream()
				.filter { it.clazz.name.endsWith('RestBeanParams') }
				.collect(Collectors.toSet())

		then:
		formParams.stream()
				.noneMatch { it.name == 'uid' }
	}

}

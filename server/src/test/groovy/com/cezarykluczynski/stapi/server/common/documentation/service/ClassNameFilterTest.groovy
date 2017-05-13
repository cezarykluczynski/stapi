package com.cezarykluczynski.stapi.server.common.documentation.service

import com.google.common.collect.Lists
import com.google.common.collect.Sets

import spock.lang.Specification

class ClassNameFilterTest extends Specification {

	private ClassNameFilter classNameFilter

	void setup() {
		classNameFilter = new ClassNameFilter()
	}

	void "gets classes ending with given suffixes"() {
		given:
		Set<String> classNameSet = Sets.newHashSet()
		classNameSet.add 'com.cezarykluczynski.stapi.server.common.documentation.service.ApiRequestModelProvider'
		classNameSet.add 'com.cezarykluczynski.stapi.server.common.documentation.service.ClassNameFilter'
		classNameSet.add 'com.cezarykluczynski.stapi.server.common.documentation.service.ReflectionReader'

		when:
		Set<Class> classSet = classNameFilter.getClassesEndingWith(classNameSet, Lists.newArrayList('Provider', 'Reader'))

		then:
		classSet.size() == 2
		classSet.contains ApiRequestModelProvider
		classSet.contains ReflectionReader
	}

}

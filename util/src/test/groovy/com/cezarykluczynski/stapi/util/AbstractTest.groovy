package com.cezarykluczynski.stapi.util

import org.apache.commons.lang3.RandomUtils
import org.assertj.core.util.Sets
import spock.lang.Specification

abstract class AbstractTest extends Specification {

	protected <T> Set<T> createSetOfRandomNumberOfMocks(Class<T> clazz) {
		int numberOfMocks = RandomUtils.nextInt(1, 11)
		int i
		Set<T> set = Sets.newHashSet()
		while (i < numberOfMocks) {
			i++
			set.add(Mock(clazz))
		}
		set
	}

}

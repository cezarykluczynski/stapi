package com.cezarykluczynski.stapi.util

import com.google.common.collect.Sets
import org.apache.commons.lang3.RandomUtils
import spock.lang.Specification

abstract class AbstractTest extends Specification {

	protected static final int PAGE_NUMBER = 2
	protected static final int PAGE_SIZE = 20

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

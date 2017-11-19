package com.cezarykluczynski.stapi.util

import org.apache.commons.lang3.RandomUtils
import org.assertj.core.util.Sets
import spock.lang.Specification

abstract class AbstractTest extends Specification {

	protected static final int PAGE_NUMBER = 2
	protected static final int PAGE_SIZE = 20
	protected static final String API_KEY = 'API_KEY'
	protected static final String SORT = 'SORT'

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

package com.cezarykluczynski.stapi.util.tool

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class RandomUtilTest extends Specification {

	void "gets random item from collection"() {
		given:
		List<Integer> integerList = Lists.newArrayList(1, 2, 3)
		Set<Integer> integerSet = Sets.newHashSet(1, 2, 3)

		expect:
		NumberUtil.inRangeInclusive(RandomUtil.randomItem(integerList), 1, 3)
		NumberUtil.inRangeInclusive(RandomUtil.randomItem(integerSet), 1, 3)
	}

}

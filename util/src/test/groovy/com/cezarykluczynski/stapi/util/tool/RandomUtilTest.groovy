package com.cezarykluczynski.stapi.util.tool

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

@SuppressWarnings('EmptyClass')
class RandomUtilTest extends Specification {

	void "gets random item from collection"() {
		given:
		List<Integer> integerList = Lists.newArrayList(1, 2, 3)
		Set<Integer> integerSet = Sets.newHashSet(1, 2, 3)

		expect:
		NumberUtil.inRangeInclusive(RandomUtil.randomItem(integerList), 1, 3)
		NumberUtil.inRangeInclusive(RandomUtil.randomItem(integerSet), 1, 3)
	}

	void "gets random enum value"() {
		expect:
		RandomUtil.randomEnumValue(EnumWithASingleValue) == EnumWithASingleValue.SINGLE_VALUE
	}

	void "returns null for empty enum random value"() {
		expect:
		RandomUtil.randomEnumValue(EmptyEnum) == null
	}

	void "throws exception when class passed to random enum value generator is not enum"() {
		when:
		RandomUtil.randomEnumValue(NotAnEnum)

		then:
		thrown(IllegalArgumentException)
	}

	private enum EnumWithASingleValue {

		SINGLE_VALUE

	}

	private enum EmptyEnum {
	}

	private class NotAnEnum {
	}

}

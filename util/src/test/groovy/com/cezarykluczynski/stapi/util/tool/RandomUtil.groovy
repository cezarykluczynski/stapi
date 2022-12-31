package com.cezarykluczynski.stapi.util.tool

import org.apache.commons.lang3.RandomUtils

import java.security.SecureRandom

class RandomUtil {

	private static final Random RANDOM = new SecureRandom()

	static boolean nextBoolean() {
		RandomUtils.nextInt(0, 2) == 0
	}

	static <T> T randomItem(Collection<T> collection) {
		if (collection.empty) {
			return null
		}

		int collectionSize = collection.size()
		int randomItemIndex = RANDOM.nextInt(collectionSize)
		int index = 0
		Iterator<T> iterator = collection.iterator()

		while (iterator.hasNext()) {
			T item = iterator.next()
			if (index == randomItemIndex) {
				return item
			}
			index++
		}

		null
	}

	static <T extends Enum> T randomEnumValue(Class<T> clazz) {
		T[] values = clazz.enumConstants
		if (values == null) {
			throw new IllegalArgumentException('Not an enum')
		}
		if (values.length == 0) {
			return null
		}
		int randomItemIndex = RANDOM.nextInt(values.length)
		values[randomItemIndex]
	}

}

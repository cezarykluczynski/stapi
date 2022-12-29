package com.cezarykluczynski.stapi.util.tool;

import org.apache.commons.lang3.RandomUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class RandomUtil {

	private static final Random RANDOM = new Random();

	public static boolean nextBoolean() {
		return RandomUtils.nextInt(0, 2) == 0;
	}

	public static <T> T randomItem(Collection<T> collection) {
		if (collection.isEmpty()) {
			return null;
		}

		int collectionSize = collection.size();
		int randomItemIndex = RANDOM.nextInt(collectionSize);
		int index = 0;
		Iterator<T> iterator = collection.iterator();

		while (iterator.hasNext()) {
			T item = iterator.next();
			if (index == randomItemIndex) {
				return item;
			}
			index++;
		}

		return null;
	}

	public static <T extends Enum> T randomEnumValue(Class<T> clazz) {
		T[] values = clazz.getEnumConstants();
		if (values == null) {
			throw new IllegalArgumentException("Not an enum");
		}
		if (values.length == 0) {
			return null;
		}
		int randomItemIndex = RANDOM.nextInt(values.length);
		return values[randomItemIndex];
	}

}

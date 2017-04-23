package com.cezarykluczynski.stapi.util.tool;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class RandomUtil {

	public static <T> T randomItem(Collection<T> collection) {
		if (collection.isEmpty()) {
			return null;
		}

		int collectionSize = collection.size();
		int randomItemIndex = new Random().nextInt(collectionSize);
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

}

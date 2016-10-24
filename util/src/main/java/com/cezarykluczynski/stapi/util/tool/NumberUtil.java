package com.cezarykluczynski.stapi.util.tool;

public class NumberUtil {

	public static Integer ensureWithinRangeInclusive(Integer min, Integer subject, Integer max) {
		if (min > max) {
			throw new IllegalArgumentException(
					String.format("Min value %s cannot is larger than max value %s", min, max));
		}

		if (subject < min) {
			return min;
		} else if (subject > max) {
			return max;
		}

		return subject;
	}

}

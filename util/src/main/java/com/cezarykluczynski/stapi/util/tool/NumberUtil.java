package com.cezarykluczynski.stapi.util.tool;

import com.google.common.base.Preconditions;

public class NumberUtil {

	private static final String SUBJECT_CANNOT_BE_NULL = "subject cannot be null";
	private static final String MIN_CANNOT_BE_NULL = "min cannot be null";
	private static final String MAX_CANNOT_BE_NULL = "max cannot be null";

	public static Integer ensureWithinRangeInclusive(Integer subject, Integer min, Integer max) {
		Preconditions.checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
		Preconditions.checkNotNull(min, MIN_CANNOT_BE_NULL);
		Preconditions.checkNotNull(max, MAX_CANNOT_BE_NULL);
		if (min > max) {
			throw new IllegalArgumentException(String.format("Min value %s cannot is larger than max value %s", min, max));
		}

		if (subject < min) {
			return min;
		} else if (subject > max) {
			return max;
		}

		return subject;
	}

	public static boolean inRangeInclusive(Integer subject, Integer min, Integer max) {
		Preconditions.checkNotNull(subject, SUBJECT_CANNOT_BE_NULL);
		Preconditions.checkNotNull(min, MIN_CANNOT_BE_NULL);
		Preconditions.checkNotNull(max, MAX_CANNOT_BE_NULL);

		return subject >= min && subject <= max;
	}

}

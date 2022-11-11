package com.cezarykluczynski.stapi.etl.util;

import org.apache.commons.lang3.StringUtils;

public class CharacterUtil {

	public static boolean isOneOfMany(String characterName) {
		if (characterName == null || characterName.length() < 5) {
			return false;
		}
		String last4Chars = StringUtils.right(characterName, 4);
		return StringUtils.isBlank(StringUtils.left(last4Chars, 1)) && StringUtils.isNumeric(StringUtils.right(last4Chars, 3));
	}

}

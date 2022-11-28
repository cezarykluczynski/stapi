package com.cezarykluczynski.stapi.etl.util;

import org.apache.commons.lang3.StringUtils;

public class TitleUtil {

	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";
	private static final String LEFT_BRACKET_JAPANESE = "（";
	private static final String RIGHT_BRACKET_JAPANESE = "）";

	public static String getNameFromTitle(String title) {
		return StringUtils.trim(StringUtils.substringBefore(StringUtils.substringBefore(title, LEFT_BRACKET), LEFT_BRACKET_JAPANESE));
	}

	public static String getNameFromTitleIfBracketsEndsString(String title) {
		if (title != null && (StringUtils.trim(title).endsWith(RIGHT_BRACKET) || StringUtils.trim(title).endsWith(RIGHT_BRACKET_JAPANESE))) {
			return getNameFromTitle(title);
		}
		return StringUtils.trim(title);
	}

	public static String toMediaWikiTitle(String title) {
		return StringUtils.replaceChars(title, " ", "_");
	}

}

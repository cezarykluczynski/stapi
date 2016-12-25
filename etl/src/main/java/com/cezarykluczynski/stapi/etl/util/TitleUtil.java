package com.cezarykluczynski.stapi.etl.util;

import org.apache.commons.lang3.StringUtils;

public class TitleUtil {

	public static String getNameFromTitle(String title) {
		return StringUtils.trim(StringUtils.substringBefore(StringUtils.substringBefore(title, "("), "（"));
	}

}

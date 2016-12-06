package com.cezarykluczynski.stapi.etl.template.util;

import java.util.regex.Pattern;

public class PatternDictionary {

	public static final Pattern YEAR = Pattern.compile("^\\d{4}$");

	public static final String MONTH_GROUP = "(January|February|March|April|May|June|July|August|September|October|November|December)";

}

package com.cezarykluczynski.stapi.etl.template.util;

import java.util.regex.Pattern;

public class PatternDictionary {

	public static final String PATTERN_START = "^";
	public static final String PATTERN_END = "$";
	public static final String SPACE = "\\s";
	public static final String YEAR_GROUP = "(\\d{4})";
	public static final String MONTH_GROUP = "(January|February|March|April|May|June|July|August|September|October|November|December)";
	public static final String MONTH_GROUP_SHORT = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
	public static final String DAY_GROUP = "(\\d{1,2})((st|nd|rd|th)?)";
	public static final String BR = "<br\\s?/?>";

	public static final Pattern YEAR = Pattern.compile(PATTERN_START + YEAR_GROUP + PATTERN_END);

}

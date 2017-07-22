package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextToDayMonthYearProcessor implements ItemProcessor<String, DayMonthYear> {

	private static final Map<String, String> SHORT_TO_FULL_MONTHS = Maps.newHashMap();

	static {
		final String may = "MAY";
		SHORT_TO_FULL_MONTHS.put("JAN", "JANUARY");
		SHORT_TO_FULL_MONTHS.put("FEB", "FEBRUARY");
		SHORT_TO_FULL_MONTHS.put("MAR", "MARCH");
		SHORT_TO_FULL_MONTHS.put("APR", "APRIL");
		SHORT_TO_FULL_MONTHS.put(may, may);
		SHORT_TO_FULL_MONTHS.put("JUN", "JUNE");
		SHORT_TO_FULL_MONTHS.put("JUL", "JULY");
		SHORT_TO_FULL_MONTHS.put("AUG", "AUGUST");
		SHORT_TO_FULL_MONTHS.put("SEP", "SEPTEMBER");
		SHORT_TO_FULL_MONTHS.put("OCT", "OCTOBER");
		SHORT_TO_FULL_MONTHS.put("NOV", "NOVEMBER");
		SHORT_TO_FULL_MONTHS.put("DEC", "DECEMBER");
	}

	private static final Pattern DAY_MONTH_YEAR = Pattern.compile(PatternDictionary.PATTERN_START + PatternDictionary.DAY_GROUP
			+ PatternDictionary.SPACE + PatternDictionary.MONTH_GROUP + PatternDictionary.SPACE + PatternDictionary.YEAR_GROUP
			+ PatternDictionary.PATTERN_END);
	private static final Pattern DAY_MONTH_SHORT_YEAR = Pattern.compile(PatternDictionary.PATTERN_START + PatternDictionary.DAY_GROUP
			+ PatternDictionary.SPACE + PatternDictionary.MONTH_GROUP_SHORT + PatternDictionary.SPACE + PatternDictionary.YEAR_GROUP
			+ PatternDictionary.PATTERN_END);
	private static final Pattern MONTH_YEAR = Pattern.compile(PatternDictionary.PATTERN_START + PatternDictionary.MONTH_GROUP
			+ PatternDictionary.SPACE + PatternDictionary.YEAR_GROUP + PatternDictionary.PATTERN_END);
	private static final Pattern MONTH_SHORT_YEAR = Pattern.compile(PatternDictionary.PATTERN_START + PatternDictionary.MONTH_GROUP_SHORT
			+ PatternDictionary.SPACE + PatternDictionary.YEAR_GROUP + PatternDictionary.PATTERN_END);
	private static final Pattern YEAR = Pattern.compile("([0-9]{4})");

	@Override
	@SuppressWarnings("ReturnCount")
	public DayMonthYear process(String item) throws Exception {
		if (StringUtils.isEmpty(item)) {
			return null;
		}

		Matcher dayMonthYearMatcher = DAY_MONTH_YEAR.matcher(item);

		if (dayMonthYearMatcher.matches()) {
			return fromDayMonthYearMatcher(dayMonthYearMatcher);
		}

		Matcher dayMonthShortYearMatcher = DAY_MONTH_SHORT_YEAR.matcher(item);

		if (dayMonthShortYearMatcher.matches()) {
			return fromDayMonthShortYearMatcher(dayMonthShortYearMatcher);
		}

		Matcher monthYearMatcher = MONTH_YEAR.matcher(item);

		if (monthYearMatcher.matches()) {
			return fromMonthYearMatcher(monthYearMatcher);
		}

		Matcher monthShortYearMatcher = MONTH_SHORT_YEAR.matcher(item);

		if (monthShortYearMatcher.matches()) {
			return fromMonthShortYearMatcher(monthShortYearMatcher);
		}

		return fromYearMatcher(YEAR.matcher(item));
	}

	private DayMonthYear fromDayMonthYearMatcher(Matcher dayMonthYearMatcher) {
		Integer day = Ints.tryParse(dayMonthYearMatcher.group(1));
		Integer month = Month.valueOf(StringUtils.upperCase(dayMonthYearMatcher.group(4))).getValue();
		Integer year = Ints.tryParse(dayMonthYearMatcher.group(5));
		return DayMonthYear.of(day, month, year);
	}

	private DayMonthYear fromDayMonthShortYearMatcher(Matcher dayMonthShortYearMatcher) {
		Integer day = Ints.tryParse(dayMonthShortYearMatcher.group(1));
		Integer month = Month.valueOf(SHORT_TO_FULL_MONTHS.get(StringUtils.upperCase(dayMonthShortYearMatcher.group(4)))).getValue();
		Integer year = Ints.tryParse(dayMonthShortYearMatcher.group(5));
		return DayMonthYear.of(day, month, year);
	}

	private DayMonthYear fromMonthYearMatcher(Matcher monthYearMatcher) {
		Integer month = Month.valueOf(StringUtils.upperCase(monthYearMatcher.group(1))).getValue();
		Integer year = Ints.tryParse(monthYearMatcher.group(2));
		return DayMonthYear.of(null, month, year);
	}

	private DayMonthYear fromMonthShortYearMatcher(Matcher monthShortYearMatcher) {
		Integer month = Month.valueOf(SHORT_TO_FULL_MONTHS.get(StringUtils.upperCase(monthShortYearMatcher.group(1)))).getValue();
		Integer year = Ints.tryParse(monthShortYearMatcher.group(2));
		return DayMonthYear.of(null, month, year);
	}

	private DayMonthYear fromYearMatcher(Matcher yearMatcher) {
		List<String> years = Lists.newArrayList();

		while (yearMatcher.find()) {
			years.add(yearMatcher.group(1));
		}

		Integer year = years.stream()
				.map(Ints::tryParse)
				.filter(Objects::nonNull)
				.sorted()
				.findFirst()
				.orElse(null);

		return year == null ? null : DayMonthYear.of(null, null, year);
	}

}

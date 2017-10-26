package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.individual.dto.DayMonthDTO;
import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DayMonthPageLinkProcessor implements ItemProcessor<PageLink, DayMonthDTO> {

	private static final String OPTIONAL_SPACE_GROUP = "(\\s?)";

	private static final Pattern DAY_MONTH = Pattern.compile(PatternDictionary.DAY_GROUP + OPTIONAL_SPACE_GROUP + PatternDictionary.MONTH_GROUP);

	private static final Pattern MONTH_DAY = Pattern.compile(PatternDictionary.MONTH_GROUP + OPTIONAL_SPACE_GROUP + PatternDictionary.DAY_GROUP);

	private static final Integer LEFT_GROUP = 1;

	private static final Integer RIGHT_GROUP = 4;

	private final MonthNameToMonthProcessor monthNameToMonthProcessor;

	public DayMonthPageLinkProcessor(MonthNameToMonthProcessor monthNameToMonthProcessor) {
		this.monthNameToMonthProcessor = monthNameToMonthProcessor;
	}

	@Override
	public DayMonthDTO process(PageLink item) throws Exception {
		if (item == null || item.getTitle() == null) {
			return DayMonthDTO.of(null, null);
		}

		String pageLinkTitle = TitleUtil.getNameFromTitle(item.getTitle());
		Month month = monthFromString(pageLinkTitle);

		return tryParseExtractDayMonth(item, month);
	}

	private DayMonthDTO tryParseExtractDayMonth(PageLink item, Month month) throws Exception {
		String pageLinkDescription = item.getDescription();
		if (pageLinkDescription == null) {
			return DayMonthDTO.of(null, month);
		}

		Matcher dayMonthMatcher = DAY_MONTH.matcher(pageLinkDescription);
		Matcher monthDayMatcher = MONTH_DAY.matcher(pageLinkDescription);

		if (dayMonthMatcher.matches()) {
			String group = dayMonthMatcher.group(LEFT_GROUP);
			Month monthFromMatch = null;

			if (month == null) {
				monthFromMatch = ObjectUtils.firstNonNull(monthFromString(dayMonthMatcher.group(RIGHT_GROUP)),
						monthFromString(dayMonthMatcher.group(RIGHT_GROUP + 1)));
			}

			return DayMonthDTO.of(Integer.valueOf(group), ObjectUtils.firstNonNull(month, monthFromMatch));
		}

		if (monthDayMatcher.matches()) {
			String group = monthDayMatcher.group(RIGHT_GROUP - 1);
			Month monthFromMatch = null;

			if (month == null) {
				monthFromMatch = monthFromString(monthDayMatcher.group(LEFT_GROUP));
			}

			return DayMonthDTO.of(Integer.valueOf(group), ObjectUtils.firstNonNull(month, monthFromMatch));
		}

		return DayMonthDTO.of(null, month);
	}

	private Month monthFromString(String possibleMonth) throws Exception {
		return monthNameToMonthProcessor.process(possibleMonth);
	}

}

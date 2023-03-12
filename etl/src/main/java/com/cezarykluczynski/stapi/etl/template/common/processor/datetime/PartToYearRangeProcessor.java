package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PartToYearRangeProcessor implements ItemProcessor<Template.Part, YearRange> {

	private static final Pattern PATTERN = Pattern.compile("^([\\d\\s]){0,5}(&ndash;|\\sto\\s)([\\d\\s]){0,5}$");

	private final YearlinkToYearProcessor yearlinkToYearProcessor;

	private final MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToMonthYearCandiateProcessor;

	private final TemplateFilter templateFilter;

	private final WikitextApi wikitextApi;

	public PartToYearRangeProcessor(YearlinkToYearProcessor yearlinkToYearProcessor,
			MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToMonthYearCandiateProcessor, TemplateFilter templateFilter,
			WikitextApi wikitextApi) {
		this.yearlinkToYearProcessor = yearlinkToYearProcessor;
		this.monthlinkTemplateToMonthYearCandiateProcessor = monthlinkTemplateToMonthYearCandiateProcessor;
		this.templateFilter = templateFilter;
		this.wikitextApi = wikitextApi;
	}

	@Override
	public YearRange process(Template.Part item) throws Exception {
		String value = item.getValue();
		YearRange yearRange = new YearRange();
		if (StringUtils.isNotBlank(value)) {
			enrichWithValue(value, yearRange);
		}

		if (yearRange.getYearFrom() != null && yearRange.getYearTo() != null) {
			return yearRange;
		}

		List<Template> templateList = item.getTemplates();
		if (!CollectionUtils.isEmpty(templateList)) {
			enrichWithTemplates(templateList, yearRange);
		}

		return yearRange;
	}

	private void enrichWithValue(String value, YearRange yearRange) {
		Matcher matcher = PATTERN.matcher(value);
		if (matcher.matches()) {
			final int start = matcher.start(2);
			final int end = matcher.end(2);
			String from = value.substring(0, start);
			String to = value.substring(end);
			yearRange.setYearFrom(tryParseIntoYear(from));
			yearRange.setYearTo(tryParseIntoYear(to));
		}
		if (yearRange.getYearFrom() == null && yearRange.getYearTo() == null) {
			yearRange.setYearFrom(tryParseIntoYear(value));
		}
	}

	@SuppressWarnings("NPathComplexity")
	private void enrichWithTemplates(List<Template> templateList, YearRange yearRange) throws Exception {
		List<Template> yearTemplateList = templateFilter
				.filterByTitle(templateList, TemplateTitle.Y, TemplateTitle.YEARLINK);
		List<Template> monthTemplateList = templateFilter
				.filterByTitle(templateList, TemplateTitle.M, TemplateTitle.MONTHLINK);

		if (CollectionUtils.isEmpty(yearTemplateList) && CollectionUtils.isEmpty(monthTemplateList)) {
			return;
		}

		int yearsSize = yearTemplateList.size();

		if (yearsSize == 1) {
			final Integer yearFromTemplate = yearlinkToYearProcessor.process(yearTemplateList.get(0));
			if (yearRange.getYearFrom() == null) {
				yearRange.setYearFrom(yearFromTemplate);
			} else if (yearRange.getYearTo() == null) {
				yearRange.setYearTo(yearFromTemplate);
			}
		} else if (yearsSize == 2) {
			yearRange.setYearFrom(yearlinkToYearProcessor.process(yearTemplateList.get(0)));
			yearRange.setYearTo(yearlinkToYearProcessor.process(yearTemplateList.get(1)));
		} else if (yearsSize > 2) {
			log.warn("More than 2 year templates found in template list: {}.", yearTemplateList);
		}

		if (yearRange.getYearFrom() != null && yearRange.getYearTo() != null) {
			return;
		}

		int monthsSize = monthTemplateList.size();

		if (monthsSize == 1) {
			final MonthYearCandidate monthYearCandidate = monthlinkTemplateToMonthYearCandiateProcessor.process(monthTemplateList.get(0));
			if (monthYearCandidate != null) {
				final Integer yearFromTemplate = monthYearCandidate.getYear();
				if (yearRange.getYearFrom() == null) {
					yearRange.setYearFrom(yearFromTemplate);
				} else if (yearRange.getYearTo() == null) {
					yearRange.setYearTo(yearFromTemplate);
				}
			}
		} else if (monthsSize == 2) {
			final MonthYearCandidate monthYearFrom = monthlinkTemplateToMonthYearCandiateProcessor.process(yearTemplateList.get(0));
			final MonthYearCandidate monthYearTo = monthlinkTemplateToMonthYearCandiateProcessor.process(yearTemplateList.get(1));
			if (monthYearFrom != null) {
				yearRange.setYearFrom(monthYearFrom.getYear());
			}
			if (monthYearTo != null) {
				yearRange.setYearTo(monthYearTo.getYear());
			}
		} else if (monthsSize > 2) {
			log.warn("More than 2 month templates found in template list: {}.", yearTemplateList);
		}
	}

	private Integer tryParseIntoYear(String wikitext) {
		final String wikitextWithoutLinks = StringUtils.trim(wikitextApi.getWikitextWithoutLinks(wikitext));
		final Integer candidate = Ints.tryParse(wikitextWithoutLinks);
		if (candidate != null && candidate > 1950 && candidate < LocalDateTime.now().getYear() + 10) {
			return candidate;
		}
		return null;
	}

}

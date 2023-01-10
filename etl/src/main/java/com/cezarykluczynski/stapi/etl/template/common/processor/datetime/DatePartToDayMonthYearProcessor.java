package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DatePartToDayMonthYearProcessor implements ItemProcessor<Template.Part, DayMonthYear> {

	private final TemplateFilter templateFilter;

	private final TemplateToDayMonthYearParser templateToDayMonthYearParser;

	public DatePartToDayMonthYearProcessor(TemplateFilter templateFilter, TemplateToDayMonthYearParser templateToDayMonthYearParser) {
		this.templateFilter = templateFilter;
		this.templateToDayMonthYearParser = templateToDayMonthYearParser;
	}

	@Override
	public DayMonthYear process(Template.Part item) throws Exception {
		final List<Template> templates = item.getTemplates();
		List<Template> dayTemplateList = templateFilter.filterByTitle(templates, TemplateTitle.D, TemplateTitle.DATELINK);
		List<Template> monthTemplateList = templateFilter.filterByTitle(templates, TemplateTitle.M, TemplateTitle.MONTHLINK);
		List<Template> yearTemplateList = templateFilter.filterByTitle(templates, TemplateTitle.Y, TemplateTitle.YEARLINK);

		DayMonthYear dayMonthYear = null;

		if (!dayTemplateList.isEmpty()) {
			List<DayMonthYear> dayMonthYears = dayTemplateList.stream()
					.map(templateToDayMonthYearParser::parseDayMonthYearCandidate)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			if (!dayMonthYears.isEmpty()) {
				dayMonthYear = dayMonthYears.get(0);
			}
		}

		if (dayMonthYear == null && !monthTemplateList.isEmpty()) {
			List<DayMonthYear> dayMonthYears = monthTemplateList.stream()
					.map(templateToDayMonthYearParser::parseMonthYearCandidate)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			if (!dayMonthYears.isEmpty()) {
				dayMonthYear = dayMonthYears.get(0);
			}
		}

		if (dayMonthYear == null && !yearTemplateList.isEmpty()) {
			final List<DayMonthYear> dayMonthYears = yearTemplateList.stream()
					.map(templateToDayMonthYearParser::parseYearCandidate)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			if (!dayMonthYears.isEmpty()) {
				dayMonthYear = dayMonthYears.get(0);
			}
		}

		return dayMonthYear;
	}

}

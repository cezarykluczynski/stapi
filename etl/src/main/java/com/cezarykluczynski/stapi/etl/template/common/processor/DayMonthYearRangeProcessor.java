package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DayMonthYearRangeProcessor implements ItemProcessor<Template.Part, Range<DayMonthYear>> {

	private static final Integer MIN = 1;
	private static final Integer MAX = 2;
	private static final Integer INDEX_FIRST = 0;
	private static final Integer INDEX_SECOND = 1;

	private final TemplateFilter templateFilter;

	private final TemplateToDayMonthYearParser templateToDayMonthYearParser;

	public DayMonthYearRangeProcessor(TemplateFilter templateFilter, TemplateToDayMonthYearParser templateToDayMonthYearParser) {
		this.templateFilter = templateFilter;
		this.templateToDayMonthYearParser = templateToDayMonthYearParser;
	}

	@Override
	public Range<DayMonthYear> process(Template.Part item) throws Exception {
		List<Template> dayTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateTitle.D, TemplateTitle.DATELINK);
		List<Template> monthTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateTitle.M, TemplateTitle.MONTHLINK);
		List<Template> yearTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateTitle.Y, TemplateTitle.YEARLINK);

		DayMonthYear dayMonthYearFrom;
		DayMonthYear dayMonthYearTo = null;

		if (NumberUtil.inRangeInclusive(dayTemplateList.size(), MIN, MAX)) {
			dayMonthYearFrom = parseDayMonthYearCandidate(dayTemplateList.get(INDEX_FIRST));

			if (dayTemplateList.size() == MAX) {
				dayMonthYearTo = parseDayMonthYearCandidate(dayTemplateList.get(INDEX_SECOND));
			}
		} else if (NumberUtil.inRangeInclusive(monthTemplateList.size(), MIN, MAX)) {
			dayMonthYearFrom = parseMonthYearCandidate(monthTemplateList.get(INDEX_FIRST));

			if (monthTemplateList.size() == MAX) {
				dayMonthYearTo = parseMonthYearCandidate(monthTemplateList.get(INDEX_SECOND));
			}
		} else if (NumberUtil.inRangeInclusive(yearTemplateList.size(), MIN, MAX)) {
			dayMonthYearFrom = parseYearCandidate(yearTemplateList.get(INDEX_FIRST));

			if (yearTemplateList.size() == MAX) {
				dayMonthYearTo = parseYearCandidate(yearTemplateList.get(INDEX_SECOND));
			}
		} else {
			return Range.of(null, null);
		}

		return Range.of(dayMonthYearFrom, dayMonthYearTo);
	}

	private DayMonthYear parseDayMonthYearCandidate(Template template) throws Exception {
		return templateToDayMonthYearParser.parseDayMonthYearCandidate(template);
	}

	private DayMonthYear parseMonthYearCandidate(Template template) throws Exception {
		return templateToDayMonthYearParser.parseMonthYearCandidate(template);
	}

	private DayMonthYear parseYearCandidate(Template template) throws Exception {
		return templateToDayMonthYearParser.parseYearCandidate(template);
	}

}

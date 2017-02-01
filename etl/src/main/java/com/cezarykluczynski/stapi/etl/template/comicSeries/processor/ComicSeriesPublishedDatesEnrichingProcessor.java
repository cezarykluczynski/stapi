package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthlinkTemplateToMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class ComicSeriesPublishedDatesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, ComicSeriesTemplate>> {

	private static final Integer MIN = 1;
	private static final Integer MAX = 2;
	private static final Integer INDEX_FIRST = 0;
	private static final Integer INDEX_SECOND = 1;

	private TemplateFilter templateFilter;

	private DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessor;

	private MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessor;

	private YearlinkToYearProcessor yearlinkToYearProcessor;

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	@Inject
	public ComicSeriesPublishedDatesEnrichingProcessor(TemplateFilter templateFilter,
			DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessor,
			MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessor, YearlinkToYearProcessor yearlinkToYearProcessor,
			ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessor) {
		this.templateFilter = templateFilter;
		this.datelinkTemplateToDayMonthYearProcessor = datelinkTemplateToDayMonthYearProcessor;
		this.monthlinkTemplateToMonthYearProcessor = monthlinkTemplateToMonthYearProcessor;
		this.yearlinkToYearProcessor = yearlinkToYearProcessor;
		this.comicSeriesTemplateDayMonthYearRangeEnrichingProcessor = comicSeriesTemplateDayMonthYearRangeEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();

		List<Template> dayTemplateList = templateFilter.filterByTitle(templatePart.getTemplates(), TemplateName.D, TemplateName.DATELINK);
		List<Template> monthTemplateList = templateFilter.filterByTitle(templatePart.getTemplates(), TemplateName.M, TemplateName.MONTHLINK);
		List<Template> yearTemplateList = templateFilter.filterByTitle(templatePart.getTemplates(), TemplateName.Y, TemplateName.YEARLINK);

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
			return;
		}

		Range<DayMonthYear> dayMonthYearRange = Range.of(dayMonthYearFrom, dayMonthYearTo);
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(dayMonthYearRange, comicSeriesTemplate));
	}

	private DayMonthYear parseYearCandidate(Template template) throws Exception {
		Integer year = yearlinkToYearProcessor.process(template);

		if (year == null) {
			return null;
		}

		DayMonthYear dayMonthYear = new DayMonthYear();
		dayMonthYear.setYear(year);
		return dayMonthYear;
	}

	private DayMonthYear parseDayMonthYearCandidate(Template template) throws Exception {
		return datelinkTemplateToDayMonthYearProcessor.process(template);
	}

	private DayMonthYear parseMonthYearCandidate(Template template) throws Exception {
		return monthlinkTemplateToMonthYearProcessor.process(template);
	}

}

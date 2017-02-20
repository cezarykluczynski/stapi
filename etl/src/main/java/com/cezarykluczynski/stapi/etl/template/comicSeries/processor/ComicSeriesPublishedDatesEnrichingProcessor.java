package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class ComicSeriesPublishedDatesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, ComicSeriesTemplate>> {

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessor;

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	@Inject
	public ComicSeriesPublishedDatesEnrichingProcessor(DayMonthYearRangeProcessor dayMonthYearRangeProcessor,
			ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessor) {
		this.dayMonthYearRangeProcessor = dayMonthYearRangeProcessor;
		this.comicSeriesTemplateDayMonthYearRangeEnrichingProcessor = comicSeriesTemplateDayMonthYearRangeEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();

		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart);

		if (dayMonthYearRange == null || dayMonthYearRange.getFrom() == null && dayMonthYearRange.getTo() == null) {
			return;
		}

		comicSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(dayMonthYearRange, comicSeriesTemplate));
	}

}

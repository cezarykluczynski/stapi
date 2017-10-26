package com.cezarykluczynski.stapi.etl.template.publishable_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublishableSeriesPublishedDatesEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<PublishableSeriesTemplate> {

	private final DayMonthYearRangeProcessor dayMonthYearRangeProcessor;

	private final PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	public PublishableSeriesPublishedDatesEnrichingProcessor(DayMonthYearRangeProcessor dayMonthYearRangeProcessor,
			PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor) {
		this.dayMonthYearRangeProcessor = dayMonthYearRangeProcessor;
		this.publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor = publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, PublishableSeriesTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		PublishableSeriesTemplate publishableSeriesTemplate = enrichablePair.getOutput();

		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart);

		if (dayMonthYearRange == null || dayMonthYearRange.getFrom() == null && dayMonthYearRange.getTo() == null) {
			return;
		}

		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
				.enrich(EnrichablePair.of(Pair.of(templatePart, dayMonthYearRange), publishableSeriesTemplate));
	}

}

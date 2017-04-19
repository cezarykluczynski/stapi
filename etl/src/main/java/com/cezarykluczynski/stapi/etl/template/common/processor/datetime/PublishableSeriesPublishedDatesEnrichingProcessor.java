package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class PublishableSeriesPublishedDatesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Template.Part, PublishableSeriesTemplate>> {

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessor;

	private PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	@Inject
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

		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(dayMonthYearRange, publishableSeriesTemplate));
	}

}

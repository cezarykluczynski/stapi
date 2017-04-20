package com.cezarykluczynski.stapi.etl.template.publishableSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.publishableSeries.dto.PublishableSeriesTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Range<DayMonthYear>, PublishableSeriesTemplate>> {

	@Override
	public void enrich(EnrichablePair<Range<DayMonthYear>, PublishableSeriesTemplate> enrichablePair) throws Exception {
		Range<DayMonthYear> dayMonthYearRange = enrichablePair.getInput();
		PublishableSeriesTemplate publishableSeriesTemplate = enrichablePair.getOutput();

		if (dayMonthYearRange == null || publishableSeriesTemplate == null) {
			return;
		}

		DayMonthYear dayMonthYearFrom = dayMonthYearRange.getFrom();
		DayMonthYear dayMonthYearTo = dayMonthYearRange.getTo();

		if (dayMonthYearFrom != null) {
			publishableSeriesTemplate.setPublishedYearFrom(dayMonthYearFrom.getYear());
			publishableSeriesTemplate.setPublishedMonthFrom(dayMonthYearFrom.getMonth());
			publishableSeriesTemplate.setPublishedDayFrom(dayMonthYearFrom.getDay());
		}

		if (dayMonthYearTo != null) {
			publishableSeriesTemplate.setPublishedYearTo(dayMonthYearTo.getYear());
			publishableSeriesTemplate.setPublishedMonthTo(dayMonthYearTo.getMonth());
			publishableSeriesTemplate.setPublishedDayTo(dayMonthYearTo.getDay());
		} else if (dayMonthYearFrom != null) {
			publishableSeriesTemplate.setPublishedYearTo(dayMonthYearFrom.getYear());
			publishableSeriesTemplate.setPublishedMonthTo(dayMonthYearFrom.getMonth());
			publishableSeriesTemplate.setPublishedDayTo(dayMonthYearFrom.getDay());
		}
	}

}

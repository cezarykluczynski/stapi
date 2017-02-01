package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate>> {

	@Override
	public void enrich(EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair) throws Exception {
		Range<DayMonthYear> dayMonthYearRange = enrichablePair.getInput();
		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();

		if (dayMonthYearRange == null || comicSeriesTemplate == null) {
			return;
		}

		DayMonthYear dayMonthYearFrom = dayMonthYearRange.getFrom();
		DayMonthYear dayMonthYearTo = dayMonthYearRange.getTo();

		if (dayMonthYearFrom != null) {
			comicSeriesTemplate.setPublishedYearFrom(dayMonthYearFrom.getYear());
			comicSeriesTemplate.setPublishedMonthFrom(dayMonthYearFrom.getMonth());
			comicSeriesTemplate.setPublishedDayFrom(dayMonthYearFrom.getDay());
		}

		if (dayMonthYearTo != null) {
			comicSeriesTemplate.setPublishedYearTo(dayMonthYearTo.getYear());
			comicSeriesTemplate.setPublishedMonthTo(dayMonthYearTo.getMonth());
			comicSeriesTemplate.setPublishedDayTo(dayMonthYearTo.getDay());
		} else if (dayMonthYearFrom != null) {
			comicSeriesTemplate.setPublishedYearTo(dayMonthYearFrom.getYear());
			comicSeriesTemplate.setPublishedMonthTo(dayMonthYearFrom.getMonth());
			comicSeriesTemplate.setPublishedDayTo(dayMonthYearFrom.getDay());
		}
	}

}

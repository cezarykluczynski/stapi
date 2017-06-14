package com.cezarykluczynski.stapi.etl.template.publishable_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Pair<Template.Part, Range<DayMonthYear>>, PublishableSeriesTemplate>> {

	private static final String[] DASHES = {"&ndash;", "-"};

	@Override
	public void enrich(EnrichablePair<Pair<Template.Part, Range<DayMonthYear>>, PublishableSeriesTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput().getKey();
		Range<DayMonthYear> dayMonthYearRange = enrichablePair.getInput().getValue();
		PublishableSeriesTemplate publishableSeriesTemplate = enrichablePair.getOutput();

		if (dayMonthYearRange == null || publishableSeriesTemplate == null) {
			return;
		}

		boolean isOpenRange = templatePart != null && StringUtils.containsAny(templatePart.getContent(), DASHES)
				&& templatePart.getTemplates().size() == 1;

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
		} else if (dayMonthYearFrom != null && !isOpenRange) {
			publishableSeriesTemplate.setPublishedYearTo(dayMonthYearFrom.getYear());
			publishableSeriesTemplate.setPublishedMonthTo(dayMonthYearFrom.getMonth());
			publishableSeriesTemplate.setPublishedDayTo(dayMonthYearFrom.getDay());
		}
	}

}

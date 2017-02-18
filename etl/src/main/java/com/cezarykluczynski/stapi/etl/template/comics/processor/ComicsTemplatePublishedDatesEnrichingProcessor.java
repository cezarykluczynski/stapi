package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsTemplatePublishedDatesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, ComicsTemplate>> {

	private ComicsTemplatePartToDayMonthRangeProcessor comicsTemplatePartToDayMonthRangeProcessor;

	@Inject
	public ComicsTemplatePublishedDatesEnrichingProcessor(ComicsTemplatePartToDayMonthRangeProcessor comicsTemplatePartToDayMonthRangeProcessor) {
		this.comicsTemplatePartToDayMonthRangeProcessor = comicsTemplatePartToDayMonthRangeProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicsTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		String templatePartKey = templatePart.getKey();

		DayMonthYear dayMonthYear = comicsTemplatePartToDayMonthRangeProcessor.process(templatePart);

		if (dayMonthYear == null) {
			return;
		}

		if (ComicsTemplateParameter.PUBLISHED.equals(templatePartKey)) {
			comicsTemplate.setPublishedYear(dayMonthYear.getYear());
			comicsTemplate.setPublishedMonth(dayMonthYear.getMonth());
			comicsTemplate.setPublishedDay(dayMonthYear.getDay());
		} else if (ComicsTemplateParameter.COVER_DATE.equals(templatePartKey)) {
			comicsTemplate.setCoverYear(dayMonthYear.getYear());
			comicsTemplate.setCoverMonth(dayMonthYear.getMonth());
			comicsTemplate.setCoverDay(dayMonthYear.getDay());
		}
	}

}

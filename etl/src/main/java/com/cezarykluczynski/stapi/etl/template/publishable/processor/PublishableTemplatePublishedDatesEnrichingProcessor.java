package com.cezarykluczynski.stapi.etl.template.publishable.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.publishable.dto.PublishableTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class PublishableTemplatePublishedDatesEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<PublishableTemplate> {

	private final DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor;

	public PublishableTemplatePublishedDatesEnrichingProcessor(DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor) {
		this.datePartToDayMonthYearProcessor = datePartToDayMonthYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, PublishableTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		PublishableTemplate publishableTemplate = enrichablePair.getOutput();
		String templatePartKey = templatePart.getKey();

		DayMonthYear dayMonthYear = datePartToDayMonthYearProcessor.process(templatePart);

		if (dayMonthYear == null) {
			return;
		}

		if (ComicsTemplateParameter.PUBLISHED.equals(templatePartKey) || MagazineTemplateParameter.RELEASED.equals(templatePartKey)) {
			publishableTemplate.setPublishedYear(dayMonthYear.getYear());
			publishableTemplate.setPublishedMonth(dayMonthYear.getMonth());
			publishableTemplate.setPublishedDay(dayMonthYear.getDay());
		} else if (ComicsTemplateParameter.COVER_DATE.equals(templatePartKey) || MagazineTemplateParameter.COVER_DATE.equals(templatePartKey)) {
			publishableTemplate.setCoverYear(dayMonthYear.getYear());
			publishableTemplate.setCoverMonth(dayMonthYear.getMonth());
			publishableTemplate.setCoverDay(dayMonthYear.getDay());
		}
	}

}

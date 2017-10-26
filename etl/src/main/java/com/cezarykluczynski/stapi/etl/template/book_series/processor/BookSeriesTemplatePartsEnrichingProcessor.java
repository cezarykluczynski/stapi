package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSeriesTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<BookSeriesTemplate> {

	private final PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	public BookSeriesTemplatePartsEnrichingProcessor(PublishableSeriesTemplatePartsEnrichingProcessor
			publishableSeriesTemplatePartsEnrichingProcessor, NumberOfPartsProcessor numberOfPartsProcessor) {
		this.publishableSeriesTemplatePartsEnrichingProcessor = publishableSeriesTemplatePartsEnrichingProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, BookSeriesTemplate> enrichablePair) throws Exception {
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(enrichablePair.getInput(), enrichablePair.getOutput()));

		BookSeriesTemplate bookSeriesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case BookSeriesTemplateParameter.NOVELS:
					if (bookSeriesTemplate.getNumberOfBooks() == null) {
						bookSeriesTemplate.setNumberOfBooks(numberOfPartsProcessor.process(value));
					}
					break;
				default:
					break;
			}
		}
	}

}

package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicSeriesTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<ComicSeriesTemplate> {

	private final PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	public ComicSeriesTemplatePartsEnrichingProcessor(PublishableSeriesTemplatePartsEnrichingProcessor
			publishableSeriesTemplatePartsEnrichingProcessor, NumberOfPartsProcessor numberOfPartsProcessor) {
		this.publishableSeriesTemplatePartsEnrichingProcessor = publishableSeriesTemplatePartsEnrichingProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, ComicSeriesTemplate> enrichablePair) throws Exception {
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(enrichablePair.getInput(), enrichablePair.getOutput()));

		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case ComicSeriesTemplateParameter.ISSUES:
					if (comicSeriesTemplate.getNumberOfIssues() == null) {
						comicSeriesTemplate.setNumberOfIssues(numberOfPartsProcessor.process(value));
					}
					break;
				default:
					break;
			}
		}
	}

}

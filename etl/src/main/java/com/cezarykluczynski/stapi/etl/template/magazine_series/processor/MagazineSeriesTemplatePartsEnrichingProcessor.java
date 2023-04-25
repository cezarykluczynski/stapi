package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineSeriesTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<MagazineSeriesTemplate> {

	private final PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	public MagazineSeriesTemplatePartsEnrichingProcessor(PublishableSeriesTemplatePartsEnrichingProcessor
			publishableSeriesTemplatePartsEnrichingProcessor, NumberOfPartsProcessor numberOfPartsProcessor,
			WikitextToEntitiesProcessor wikitextToEntitiesProcessor) {
		this.publishableSeriesTemplatePartsEnrichingProcessor = publishableSeriesTemplatePartsEnrichingProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MagazineSeriesTemplate> enrichablePair) throws Exception {
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(enrichablePair.getInput(), enrichablePair.getOutput()));

		MagazineSeriesTemplate magazineSeriesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case MagazineSeriesTemplateParameter.ISSUES:
					if (magazineSeriesTemplate.getNumberOfIssues() == null) {
						magazineSeriesTemplate.setNumberOfIssues(numberOfPartsProcessor.process(value));
					}
					break;
				case MagazineSeriesTemplateParameter.EDITOR:
					magazineSeriesTemplate.getEditors().addAll(wikitextToEntitiesProcessor.findStaff(value));
					break;
				default:
					break;
			}
		}
	}

}

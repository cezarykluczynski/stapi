package com.cezarykluczynski.stapi.etl.template.magazineSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.publishableSeries.processor.PublishableSeriesTemplatePartsEnrichingProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class MagazineSeriesTemplatePartsEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, MagazineSeriesTemplate>> {

	private final PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final WikitextStaffProcessor wikitextStaffProcessor;

	@Inject
	public MagazineSeriesTemplatePartsEnrichingProcessor(PublishableSeriesTemplatePartsEnrichingProcessor
			publishableSeriesTemplatePartsEnrichingProcessor, NumberOfPartsProcessor numberOfPartsProcessor,
			WikitextStaffProcessor wikitextStaffProcessor) {
		this.publishableSeriesTemplatePartsEnrichingProcessor = publishableSeriesTemplatePartsEnrichingProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.wikitextStaffProcessor = wikitextStaffProcessor;
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
					magazineSeriesTemplate.getEditors().addAll(wikitextStaffProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}

package com.cezarykluczynski.stapi.etl.template.magazine.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.publishable.processor.PublishableTemplatePublishedDatesEnrichingProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class MagazineTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, MagazineTemplate>> {

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final WikitextStaffProcessor wikitextStaffProcessor;

	private final PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessor;

	@Inject
	public MagazineTemplatePartsEnrichingProcessor(NumberOfPartsProcessor numberOfPartsProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor, WikitextStaffProcessor wikitextStaffProcessor,
			PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessor) {
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.wikitextStaffProcessor = wikitextStaffProcessor;
		this.publishableTemplatePublishedDatesEnrichingProcessor = publishableTemplatePublishedDatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MagazineTemplate> enrichablePair) throws Exception {
		MagazineTemplate magazineTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case MagazineTemplateParameter.ISSUE:
					magazineTemplate.setIssueNumber(numberOfPartsProcessor.process(value));
					if (magazineTemplate.getIssueNumber() == null) {
						log.warn("Issue number {} could not be mapped to integer", value);
					}
					break;
				case MagazineTemplateParameter.PUBLISHER:
					magazineTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case MagazineTemplateParameter.EDITOR:
					magazineTemplate.getEditors().addAll(wikitextStaffProcessor.process(value));
					break;
				case MagazineTemplateParameter.RELEASED:
				case MagazineTemplateParameter.COVER_DATE:
					publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, magazineTemplate));
					break;
				case MagazineTemplateParameter.PAGES:
					magazineTemplate.setNumberOfPages(numberOfPartsProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}

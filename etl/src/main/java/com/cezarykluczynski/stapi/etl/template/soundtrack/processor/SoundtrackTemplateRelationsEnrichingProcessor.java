package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextStaffProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SoundtrackTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, SoundtrackTemplate>> {

	private final WikitextStaffProcessor wikitextStaffProcessor;

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	public SoundtrackTemplateRelationsEnrichingProcessor(WikitextStaffProcessor wikitextStaffProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor, ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor) {
		this.wikitextStaffProcessor = wikitextStaffProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.referencesFromTemplatePartProcessor = referencesFromTemplatePartProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, SoundtrackTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		SoundtrackTemplate soundtrackTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case SoundtrackTemplateParameter.COMPOSER:
					soundtrackTemplate.getComposers().addAll(wikitextStaffProcessor.process(value));
					break;
				case SoundtrackTemplateParameter.ADD_MUSIC:
					soundtrackTemplate.getContributors().addAll(wikitextStaffProcessor.process(value));
					break;
				case SoundtrackTemplateParameter.ORCHESTRATOR:
					soundtrackTemplate.getOrchestrators().addAll(wikitextStaffProcessor.process(value));
					break;
				case SoundtrackTemplateParameter.LABEL:
					soundtrackTemplate.getLabels().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case SoundtrackTemplateParameter.REFERENCE:
					soundtrackTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				default:
					break;
			}
		}
	}

}

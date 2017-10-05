package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SoundtrackTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<SoundtrackTemplate> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	public SoundtrackTemplateRelationsEnrichingProcessor(WikitextToEntitiesProcessor wikitextToEntitiesProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor) {
		this.wikitextToEntitiesProcessor = wikitextToEntitiesProcessor;
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
					soundtrackTemplate.getComposers().addAll(wikitextToEntitiesProcessor.findStaff(value));
					break;
				case SoundtrackTemplateParameter.ADD_MUSIC:
					soundtrackTemplate.getContributors().addAll(wikitextToEntitiesProcessor.findStaff(value));
					break;
				case SoundtrackTemplateParameter.ORCHESTRATOR:
					soundtrackTemplate.getOrchestrators().addAll(wikitextToEntitiesProcessor.findStaff(value));
					break;
				case SoundtrackTemplateParameter.LABEL:
					soundtrackTemplate.getLabels().addAll(wikitextToEntitiesProcessor.findCompanies(value));
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

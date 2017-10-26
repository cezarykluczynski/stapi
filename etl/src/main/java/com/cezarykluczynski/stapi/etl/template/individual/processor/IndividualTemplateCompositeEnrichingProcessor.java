package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class IndividualTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private final IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessor;

	private final IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor;

	public IndividualTemplateCompositeEnrichingProcessor(
			IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessor,
			IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor) {
		this.individualTemplateDateOfDeathEnrichingProcessor = individualTemplateDateOfDeathEnrichingProcessor;
		this.individualTemplatePartsEnrichingProcessor = individualTemplatePartsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		individualTemplateDateOfDeathEnrichingProcessor.enrich(enrichablePair);
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(enrichablePair.getInput().getParts(), enrichablePair.getOutput()));
	}

}

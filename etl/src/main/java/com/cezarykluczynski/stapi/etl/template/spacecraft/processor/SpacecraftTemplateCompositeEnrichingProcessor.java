package com.cezarykluczynski.stapi.etl.template.spacecraft.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	private final SpacecraftTemplateContentsEnrichingProcessor spacecraftTemplateContentsEnrichingProcessor;

	private final SpacecraftTemplateRelationsEnrichingProcessor spacecraftTemplateRelationsEnrichingProcessor;

	public SpacecraftTemplateCompositeEnrichingProcessor(SpacecraftTemplateContentsEnrichingProcessor spacecraftTemplateContentsEnrichingProcessor,
			SpacecraftTemplateRelationsEnrichingProcessor spacecraftTemplateRelationsEnrichingProcessor) {
		this.spacecraftTemplateContentsEnrichingProcessor = spacecraftTemplateContentsEnrichingProcessor;
		this.spacecraftTemplateRelationsEnrichingProcessor = spacecraftTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) throws Exception {
		spacecraftTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		spacecraftTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}

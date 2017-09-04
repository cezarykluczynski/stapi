package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StarshipTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	private final StarshipTemplateContentsEnrichingProcessor starshipTemplateContentsEnrichingProcessor;

	private final StarshipTemplateRelationsEnrichingProcessor starshipTemplateRelationsEnrichingProcessor;

	@Inject
	public StarshipTemplateCompositeEnrichingProcessor(StarshipTemplateContentsEnrichingProcessor starshipTemplateContentsEnrichingProcessor,
			StarshipTemplateRelationsEnrichingProcessor starshipTemplateRelationsEnrichingProcessor) {
		this.starshipTemplateContentsEnrichingProcessor = starshipTemplateContentsEnrichingProcessor;
		this.starshipTemplateRelationsEnrichingProcessor = starshipTemplateRelationsEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) throws Exception {
		starshipTemplateContentsEnrichingProcessor.enrich(enrichablePair);
		starshipTemplateRelationsEnrichingProcessor.enrich(enrichablePair);
	}

}

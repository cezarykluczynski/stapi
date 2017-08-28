package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;

import javax.inject.Inject;

public class StarshipTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipTemplate>> {

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
		// TODO
	}

}

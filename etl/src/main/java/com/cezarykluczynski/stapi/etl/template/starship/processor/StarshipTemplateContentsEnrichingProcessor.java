package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class StarshipTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

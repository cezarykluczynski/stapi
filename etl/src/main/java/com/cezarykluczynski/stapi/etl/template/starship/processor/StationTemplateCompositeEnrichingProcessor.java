package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class StationTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) {
		// TODO
	}

}

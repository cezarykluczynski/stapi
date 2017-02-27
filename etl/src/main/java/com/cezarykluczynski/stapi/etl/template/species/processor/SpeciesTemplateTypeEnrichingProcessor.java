package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class SpeciesTemplateTypeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, SpeciesTemplate>> {

	@Override
	public void enrich(EnrichablePair<Page, SpeciesTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class IndividualMirrorAlternateUniverseEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, IndividualTemplate>> {

	@Override
	public void enrich(EnrichablePair<Page, IndividualTemplate> enrichablePair) throws Exception {
		// TODO
	}
}

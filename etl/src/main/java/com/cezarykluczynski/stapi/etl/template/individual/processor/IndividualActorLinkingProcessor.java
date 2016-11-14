package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class IndividualActorLinkingProcessor implements
		ItemEnrichingProcessor<EnrichablePair<Template.Part, IndividualTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template.Part, IndividualTemplate> enrichablePair) {
		// TODO
	}
}

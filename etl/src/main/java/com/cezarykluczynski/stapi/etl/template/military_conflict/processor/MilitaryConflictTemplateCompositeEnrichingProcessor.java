package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, MilitaryConflictTemplate>> {

	@Override
	public void enrich(EnrichablePair<Page, MilitaryConflictTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

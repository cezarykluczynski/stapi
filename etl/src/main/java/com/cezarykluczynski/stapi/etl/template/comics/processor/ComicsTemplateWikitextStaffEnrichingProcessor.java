package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplateWikitextStaffEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, ComicsTemplate>> {

	@Override
	public void enrich(EnrichablePair<String, ComicsTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplateFixedValuesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<ComicsTemplate, ComicsTemplate>> {

	@Override
	public void enrich(EnrichablePair<ComicsTemplate, ComicsTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

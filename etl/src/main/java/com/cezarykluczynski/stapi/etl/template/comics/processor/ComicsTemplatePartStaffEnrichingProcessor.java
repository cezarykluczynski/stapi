package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplatePartStaffEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, ComicsTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicsTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

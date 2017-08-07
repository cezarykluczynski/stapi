package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, SoundtrackTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, SoundtrackTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

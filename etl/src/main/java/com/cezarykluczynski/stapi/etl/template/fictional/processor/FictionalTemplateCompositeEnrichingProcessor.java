package com.cezarykluczynski.stapi.etl.template.fictional.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class FictionalTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

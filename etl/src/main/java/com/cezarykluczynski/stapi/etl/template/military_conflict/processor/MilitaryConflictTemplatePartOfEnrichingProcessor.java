package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplatePartOfEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<MilitaryConflictTemplate> {

	@Override
	public void enrich(EnrichablePair<Template.Part, MilitaryConflictTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

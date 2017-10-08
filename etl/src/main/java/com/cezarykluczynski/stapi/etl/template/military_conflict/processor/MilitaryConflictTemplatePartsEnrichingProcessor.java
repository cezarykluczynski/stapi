package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryConflictTemplatePartsEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, MilitaryConflictTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MilitaryConflictTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

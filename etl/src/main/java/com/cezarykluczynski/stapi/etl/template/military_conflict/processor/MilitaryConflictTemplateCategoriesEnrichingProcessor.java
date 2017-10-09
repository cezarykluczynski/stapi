package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryConflictTemplateCategoriesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<String>, MilitaryConflictTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<String>, MilitaryConflictTemplate> enrichablePair) throws Exception {
		// TODO
	}

}

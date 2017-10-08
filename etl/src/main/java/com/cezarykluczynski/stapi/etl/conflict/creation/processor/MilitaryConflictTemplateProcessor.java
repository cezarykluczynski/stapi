package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplateProcessor implements ItemProcessor<MilitaryConflictTemplate, Conflict> {

	@Override
	public Conflict process(MilitaryConflictTemplate item) throws Exception {
		// TODO
		return null;
	}

}

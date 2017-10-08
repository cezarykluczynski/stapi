package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplatePageProcessor implements ItemProcessor<Page, MilitaryConflictTemplate> {

	@Override
	public MilitaryConflictTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}

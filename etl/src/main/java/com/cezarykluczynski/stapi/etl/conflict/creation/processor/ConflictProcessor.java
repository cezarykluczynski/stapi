package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.processor.MilitaryConflictTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ConflictProcessor extends CompositeItemProcessor<PageHeader, Conflict> {

	public ConflictProcessor(PageHeaderProcessor pageHeaderProcessor, MilitaryConflictTemplatePageProcessor militaryConflictTemplatePageProcessor,
			MilitaryConflictTemplateProcessor militaryConflictTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, militaryConflictTemplatePageProcessor, militaryConflictTemplateProcessor));
	}

}

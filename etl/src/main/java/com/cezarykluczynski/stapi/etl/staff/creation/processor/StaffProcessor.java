package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.staff.creation.configuration.StaffCreationConfiguration;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StaffProcessor extends CompositeItemProcessor<PageHeader, Staff> {

	public StaffProcessor(PageHeaderProcessor pageHeaderProcessor,
			@Qualifier(StaffCreationConfiguration.STAFF_ACTOR_TEMPLATE_PAGE_PROCESSOR) ActorTemplatePageProcessor actorTemplatePageProcessor,
			StaffActorTemplateProcessor staffActorTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, actorTemplatePageProcessor, staffActorTemplateProcessor));
	}

}

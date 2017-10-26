package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.configuration.PerformerCreationConfiguration;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PerformerProcessor extends CompositeItemProcessor<PageHeader, Performer> {

	public PerformerProcessor(PageHeaderProcessor pageHeaderProcessor,
			@Qualifier(PerformerCreationConfiguration.PERFORMER_ACTOR_TEMPLATE_PAGE_PROCESSOR)
					ActorTemplatePageProcessor actorTemplatePageProcessor,
			PerformerActorTemplateProcessor performerActorTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, actorTemplatePageProcessor, performerActorTemplateProcessor));
	}

}

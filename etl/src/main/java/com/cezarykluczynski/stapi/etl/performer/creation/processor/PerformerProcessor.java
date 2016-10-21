package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerProcessor extends CompositeItemProcessor<PageHeader, Performer> {

	private PageHeaderProcessor pageHeaderProcessor;

	private ActorTemplatePageProcessor actorTemplatePageProcessor;

	private ActorTemplateProcessor actorTemplateProcessor;

	@Inject
	public PerformerProcessor(PageHeaderProcessor pageHeaderProcessor,
			ActorTemplatePageProcessor actorTemplatePageProcessor, ActorTemplateProcessor actorTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, actorTemplatePageProcessor, actorTemplateProcessor));
	}

}

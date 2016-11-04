package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;

public class ActorTemplatePageProcessor implements ItemProcessor<Page, ActorTemplate> {

	private ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor;

	private ActorTemplateListPageProcessor actorTemplateListPageProcessor;

	public ActorTemplatePageProcessor(ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor,
			ActorTemplateListPageProcessor actorTemplateListPageProcessor) {
		this.actorTemplateSinglePageProcessor = actorTemplateSinglePageProcessor;
		this.actorTemplateListPageProcessor = actorTemplateListPageProcessor;
	}

	@Override
	public ActorTemplate process(Page item) throws Exception {
		ActorTemplate actorTemplateFromSinglePage = actorTemplateSinglePageProcessor.process(item);

		if (actorTemplateFromSinglePage != null) {
			return actorTemplateFromSinglePage;
		}

		return actorTemplateListPageProcessor.process(item);
	}

}

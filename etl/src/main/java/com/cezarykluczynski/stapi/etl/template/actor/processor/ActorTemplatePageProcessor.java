package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.performer.creation.service.ActorPageFilter;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;

public class ActorTemplatePageProcessor implements ItemProcessor<Page, ActorTemplate> {

	private final ActorPageFilter actorPageFilter;

	private final ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor;

	private final ActorTemplateListPageProcessor actorTemplateListPageProcessor;

	public ActorTemplatePageProcessor(ActorPageFilter actorPageFilter, ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor,
			ActorTemplateListPageProcessor actorTemplateListPageProcessor) {
		this.actorPageFilter = actorPageFilter;
		this.actorTemplateSinglePageProcessor = actorTemplateSinglePageProcessor;
		this.actorTemplateListPageProcessor = actorTemplateListPageProcessor;
	}

	@Override
	public ActorTemplate process(Page item) throws Exception {
		if (actorPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		ActorTemplate actorTemplateFromSinglePage = actorTemplateSinglePageProcessor.process(item);

		if (actorTemplateFromSinglePage != null) {
			return actorTemplateFromSinglePage;
		}

		return actorTemplateListPageProcessor.process(item);
	}

}

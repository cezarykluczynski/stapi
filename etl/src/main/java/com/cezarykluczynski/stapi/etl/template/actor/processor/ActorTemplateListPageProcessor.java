package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.util.constant.PageNames;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorTemplateListPageProcessor extends AbstractTemplateProcessor
		implements ItemProcessor<Page, ActorTemplate> {

	@Override
	public ActorTemplate process(Page item) throws Exception {
		if (!isGamePerformer(item)) {
			return null;
		}

		PageHeader gamePerformerPageHeader = getGamePerformerPageHeader(item);
		if (gamePerformerPageHeader == null) {
			return null;
		}

		ActorTemplate actorTemplate = new ActorTemplate();
		actorTemplate.setName(gamePerformerPageHeader.getTitle());
		actorTemplate.setPage(fromPageHeaderToPageEntity(gamePerformerPageHeader));
		actorTemplate.setVideoGamePerformer(true);
		return actorTemplate;
	}


	private boolean isGamePerformer(Page item) {
		return PageNames.STAR_TREK_GAME_PERFORMERS.equals(item.getTitle());
	}

	private PageHeader getGamePerformerPageHeader(Page item) {
		List<PageHeader> redirectPath = item.getRedirectPath();
		return redirectPath.isEmpty() ? null : redirectPath.get(redirectPath.size() - 1);
	}

}

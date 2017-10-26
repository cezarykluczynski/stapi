package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.actor.dto.LifeRangeDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorTemplateListPageProcessor implements ItemProcessor<Page, ActorTemplate> {

	private PageBindingService pageBindingService;

	private VideoGamePerformerLifeRangeFixedValueProvider videoGamePerformerLifeRangeFixedValueProvider;

	public ActorTemplateListPageProcessor(PageBindingService pageBindingService,
			VideoGamePerformerLifeRangeFixedValueProvider videoGamePerformerLifeRangeFixedValueProvider) {
		this.pageBindingService = pageBindingService;
		this.videoGamePerformerLifeRangeFixedValueProvider = videoGamePerformerLifeRangeFixedValueProvider;
	}

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
		actorTemplate.setPage(pageBindingService.fromPageHeaderToPageEntity(gamePerformerPageHeader));
		actorTemplate.setVideoGamePerformer(true);

		List<PageHeader> redirectPath = item.getRedirectPath();

		if (redirectPath.isEmpty()) {
			return actorTemplate;
		}

		FixedValueHolder<LifeRangeDTO> lifeRangeFixedValueHolder = videoGamePerformerLifeRangeFixedValueProvider
				.getSearchedValue(redirectPath.get(0).getTitle());

		if (lifeRangeFixedValueHolder.isFound()) {
			LifeRangeDTO lifeRangeDTO = lifeRangeFixedValueHolder.getValue();
			actorTemplate.setLifeRange(DateRange.of(lifeRangeDTO.getDateOfBirth(), lifeRangeDTO.getDateOfDeath()));
			actorTemplate.setPlaceOfBirth(lifeRangeDTO.getPlaceOfBirth());
			actorTemplate.setPlaceOfDeath(lifeRangeDTO.getPlaceOfDeath());
		}

		return actorTemplate;
	}


	private boolean isGamePerformer(Page item) {
		return PageTitle.STAR_TREK_GAME_PERFORMERS.equals(item.getTitle());
	}

	private PageHeader getGamePerformerPageHeader(Page item) {
		List<PageHeader> redirectPath = item.getRedirectPath();
		return redirectPath.isEmpty() ? null : redirectPath.get(redirectPath.size() - 1);
	}

}

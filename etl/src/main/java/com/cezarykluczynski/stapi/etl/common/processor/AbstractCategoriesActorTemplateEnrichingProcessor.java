package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCategoriesActorTemplateEnrichingProcessor {

	protected List<String> getCategoryTitlesList(EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair) {
		List<CategoryHeader> categoryHeaderList = enrichablePair.getInput();
		categoryHeaderList = categoryHeaderList != null ? categoryHeaderList : Lists.newArrayList();

		return categoryHeaderList.stream()
				.map(CategoryHeader::getTitle)
				.collect(Collectors.toList());
	}

}

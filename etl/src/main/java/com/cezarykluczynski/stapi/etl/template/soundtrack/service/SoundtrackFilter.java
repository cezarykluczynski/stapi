package com.cezarykluczynski.stapi.etl.template.soundtrack.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;

import javax.inject.Inject;

public class SoundtrackFilter implements MediaWikiPageFilter {

	private final CategorySortingService categorySortingService;

	@Inject
	public SoundtrackFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return categorySortingService.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SOUNDTRACKS));
	}

}

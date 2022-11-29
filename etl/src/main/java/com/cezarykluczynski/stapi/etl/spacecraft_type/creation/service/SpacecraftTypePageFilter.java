package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpacecraftTypePageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Ares V", "Base camp");

	private final CategorySortingService categorySortingService;

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || INVALID_TITLES.contains(page.getTitle())
				|| categorySortingService.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS));
	}
}
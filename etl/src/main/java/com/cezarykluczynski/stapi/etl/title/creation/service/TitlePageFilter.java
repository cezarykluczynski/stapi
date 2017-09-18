package com.cezarykluczynski.stapi.etl.title.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class TitlePageFilter implements MediaWikiPageFilter {

	private static final String RANKS = " ranks";

	private final CategorySortingService categorySortingService;

	private final TitleListCache titleListCache;

	@Inject
	public TitlePageFilter(CategorySortingService categorySortingService, TitleListCache titleListCache) {
		this.categorySortingService = categorySortingService;
		this.titleListCache = titleListCache;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (categorySortingService.isSortedOnTopOfAnyCategory(page)) {
			if (StringUtils.contains(page.getTitle(), RANKS)) {
				titleListCache.add(page);
			}

			return true;
		}

		return !page.getRedirectPath().isEmpty();
	}

}

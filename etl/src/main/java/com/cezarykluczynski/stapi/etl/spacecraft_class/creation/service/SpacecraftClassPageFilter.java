package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassPageFilter implements MediaWikiPageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed";

	private final CategorySortingService categorySortingService;

	public SpacecraftClassPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| page.getTitle().startsWith(UNNAMED_PREFIX);
	}

}

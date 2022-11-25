package com.cezarykluczynski.stapi.etl.conflict.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConflictPageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Biological war", "Conquest", "Ethnic conflict", "Waterloo", "World war");

	private final CategorySortingService categorySortingService;

	public ConflictPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}

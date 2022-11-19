package com.cezarykluczynski.stapi.etl.literature.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LiteraturePageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Bookmark", "Bookstore", "Business card", "Chapter", "Column (record-keeping)",
			"Cover", "Dialogue", "Forecast", "Library", "Headline", "Horoscope", "Library of Alexandria", "Line", "Literature", "Meter (poetry)",
			"National distributor", "Newsstand", "Notebook", "Obituary column", "Paper book", "Parchment", "Plagiarism", "Publication", "Quote",
			"Sequel", "Setting (narrative)", "Talaxian canon", "Wish list");

	private final CategorySortingService categorySortingService;

	public LiteraturePageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}

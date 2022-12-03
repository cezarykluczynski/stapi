package com.cezarykluczynski.stapi.etl.literature.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LiteraturePageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Bookmark", "Bookstore", "Business card", "Chapter", "Column (record-keeping)",
			"Cover", "Dialogue", "Forecast", "Library", "Headline", "Horoscope", "Library of Alexandria", "Line", "Literature", "Meter (poetry)",
			"National distributor", "Newsstand", "Notebook", "Obituary column", "Paper book", "Parchment", "Plagiarism", "Publication", "Quote",
			"Sequel", "Setting (narrative)", "Talaxian canon", "Wish list");

	@Getter
	private final CategorySortingService categorySortingService;

	public LiteraturePageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}

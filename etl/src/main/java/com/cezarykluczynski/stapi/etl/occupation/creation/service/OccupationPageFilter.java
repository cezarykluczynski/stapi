package com.cezarykluczynski.stapi.etl.occupation.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OccupationPageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Commando", "Occupational hazard", "Prostitution", "Public relations");
	private static final Set<String> INVALID_SUFFIXES = Sets.newHashSet(" crew", " line", " organization", " team");

	private final CategorySortingService categorySortingService;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public OccupationPageFilter(CategorySortingService categorySortingService, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categorySortingService = categorySortingService;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		String title = page.getTitle();
		return categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(title)
				|| INVALID_SUFFIXES.stream().anyMatch(title::endsWith)
				|| categoryTitlesExtractingProcessor.process(page.getCategories()).contains(CategoryTitle.LISTS);
	}

}

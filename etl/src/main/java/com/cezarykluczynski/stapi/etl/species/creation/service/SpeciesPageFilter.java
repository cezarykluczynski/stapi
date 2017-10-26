package com.cezarykluczynski.stapi.etl.species.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesPageFilter implements MediaWikiPageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed";

	private static final List<String> SPECIES_CATEGORIES = Lists.newArrayList(CategoryTitle.SPECIES, CategoryTitle.UNNAMED_SPECIES,
			CategoryTitle.NON_CORPOREALS);

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final CategorySortingService categorySortingService;

	public SpeciesPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, CategorySortingService categorySortingService) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (!page.getRedirectPath().isEmpty() || PageTitle.ENDANGERED_SPECIES.equals(page.getTitle())) {
			return true;
		}

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (categoryTitleList.contains(CategoryTitle.LISTS) || categoryTitleList.contains(CategoryTitle.BIOLOGY) || categoryTitleList.stream()
				.anyMatch(categoryTitle -> categoryTitle.startsWith(UNNAMED_PREFIX))) {
			return true;
		}

		return categorySortingService.isSortedOnTopOfAnyOfCategories(page, SPECIES_CATEGORIES);
	}

}

package com.cezarykluczynski.stapi.etl.animal.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AnimalPageFilter implements MediaWikiPageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed";
	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Pet", "Game animal", "Riding animal", "Parasite");

	private final CategorySortingService categorySortingService;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public AnimalPageFilter(CategorySortingService categorySortingService, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categorySortingService = categorySortingService;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || StringUtils.startsWith(page.getTitle(), UNNAMED_PREFIX)
				|| INVALID_TITLES.contains(page.getTitle()) || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| categoryTitlesExtractingProcessor.process(page.getCategories()).contains(CategoryTitle.INDIVIDUAL_ANIMALS);
	}

}

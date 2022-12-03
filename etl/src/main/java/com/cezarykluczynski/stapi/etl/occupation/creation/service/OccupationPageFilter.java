package com.cezarykluczynski.stapi.etl.occupation.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OccupationPageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Commando", "Occupational hazard", "Prostitution", "Public relations");
	private static final Set<String> INVALID_SUFFIXES = Sets.newHashSet(" crew", " line", " organization", " team");
	private static final Set<String> INVALID_CATEGORIES = Sets.newHashSet(CategoryTitle.LISTS);

	@Getter
	private final CategorySortingService categorySortingService;

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public OccupationPageFilter(CategorySortingService categorySortingService, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.invalidTitles(INVALID_TITLES)
				.invalidSuffixes(INVALID_SUFFIXES)
				.invalidCategories(INVALID_CATEGORIES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

}

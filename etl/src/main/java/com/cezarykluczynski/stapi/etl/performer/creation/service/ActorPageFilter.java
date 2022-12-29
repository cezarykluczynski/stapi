package com.cezarykluczynski.stapi.etl.performer.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Hiroshima", "The Calling", "The Yellowjackets",
			"Two Steps From Hell");
	private static final List<String> INVALID_CATEGORIES = Lists.newArrayList(CategoryTitles.COMPANIES);

	@Getter
	private final CategorySortingService categorySortingService;

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public ActorPageFilter(CategorySortingService categorySortingService, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategories(INVALID_CATEGORIES)
				.build());
		this.categorySortingService = categorySortingService;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

}

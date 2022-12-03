package com.cezarykluczynski.stapi.etl.soundtrack.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoundtrackPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_SUFFIXES = Lists.newArrayList(" (soundtracks)");
	private static final List<String> INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF = Lists.newArrayList(CategoryTitle.SOUNDTRACKS);

	@Getter
	private final CategorySortingService categorySortingService;

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public SoundtrackPageFilter(CategorySortingService categorySortingService, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.invalidSuffixes(INVALID_SUFFIXES)
				.invalidCategoriesToBeSortedOnTopOf(INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF)
				.build());
		this.categorySortingService = categorySortingService;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

}

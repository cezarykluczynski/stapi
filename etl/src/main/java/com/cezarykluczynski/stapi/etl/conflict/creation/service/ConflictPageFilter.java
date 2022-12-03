package com.cezarykluczynski.stapi.etl.conflict.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConflictPageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Biological war", "Conquest", "Ethnic conflict", "Waterloo", "World war");

	@Getter
	private final CategorySortingService categorySortingService;

	public ConflictPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}

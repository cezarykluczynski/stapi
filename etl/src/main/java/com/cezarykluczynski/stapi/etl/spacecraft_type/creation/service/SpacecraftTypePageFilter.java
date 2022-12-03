package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacecraftTypePageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Ares V", "Base camp");
	private static final List<String> INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF = Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS);

	@Getter
	private final CategorySortingService categorySortingService;

	public SpacecraftTypePageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategoriesToBeSortedOnTopOf(INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF)
				.build());
		this.categorySortingService = categorySortingService;
	}

}

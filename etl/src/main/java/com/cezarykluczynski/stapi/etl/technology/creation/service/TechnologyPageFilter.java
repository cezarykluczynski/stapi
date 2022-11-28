package com.cezarykluczynski.stapi.etl.technology.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Canteen", "Computer expert", "Device", "Diagnosis", "Electronics",
			"Fan mail", "Identification", "Instruction", "Machine", "Optronics", "Root", "Series", "Temporal arms race", "Utensil");

	private final CategorySortingService categorySortingService;

	public TechnologyPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyOfCategories(page, CategoryTitles.TECHNOLOGY)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}

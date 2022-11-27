package com.cezarykluczynski.stapi.etl.medical_condition.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Health");

	private final CategorySortingService categorySortingService;

	public MedicalConditionPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}

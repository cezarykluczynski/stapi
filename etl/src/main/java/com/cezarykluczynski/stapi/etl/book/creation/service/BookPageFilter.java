package com.cezarykluczynski.stapi.etl.book.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookPageFilter implements PageFilter {

	private final CategorySortingService categorySortingService;

	@Inject
	public BookPageFilter(CategorySortingService categorySortingService) {
		this.categorySortingService = categorySortingService;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (!page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)) {
			return true;
		}

		return false;
	}

}

package com.cezarykluczynski.stapi.etl.location.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class LocationPageFilter implements PageFilter {

	private final CategorySortingService categorySortingService;

	private final LocationNameFilter locationNameFilter;

	@Inject
	public LocationPageFilter(CategorySortingService categorySortingService, LocationNameFilter locationNameFilter) {
		this.categorySortingService = categorySortingService;
		this.locationNameFilter = locationNameFilter;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (!page.getRedirectPath().isEmpty()) {
			return true;
		}

		String locationName = page.getTitle();
		return Boolean.FALSE.equals(locationNameFilter.isLocation(locationName));
	}
}

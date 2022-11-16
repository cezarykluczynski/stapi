package com.cezarykluczynski.stapi.etl.location.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategoryFinder;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_LOCATIONS = Lists.newArrayList("Baldwin", "Door", "Geography", "San Francisco locations", "Flood");

	private final CategoryFinder categoryFinder;

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		String locationName = page.getTitle();
		return !page.getRedirectPath().isEmpty() || categoryFinder.hasAnyCategory(page, CategoryTitles.ORGANIZATIONS)
				|| categoryFinder.hasAnyCategory(page, Lists.newArrayList(CategoryTitle.LISTS))
				|| INVALID_LOCATIONS.contains(locationName);
	}

}

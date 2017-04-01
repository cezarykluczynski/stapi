package com.cezarykluczynski.stapi.etl.organization.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class OrganizationPageFilter implements PageFilter {

	private final CategorySortingService categorySortingService;

	private final OrganizationNameFilter organizationNameFilter;

	@Inject
	public OrganizationPageFilter(CategorySortingService categorySortingService, OrganizationNameFilter organizationNameFilter) {
		this.categorySortingService = categorySortingService;
		this.organizationNameFilter = organizationNameFilter;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (!page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)) {
			return true;
		}

		String organizationName = page.getTitle();
		Boolean isAnOrganization = organizationNameFilter.isAnOrganization(organizationName);

		if (Boolean.FALSE.equals(isAnOrganization)) {
			return true;
		}

		List<String> firstLetters = Lists.newArrayList(page.getTitle().split("(?<=[\\S])[\\S]*\\s*"));
		if (firstLetters.size() > 1 && firstLetters.stream().allMatch(letter -> letter.equals(letter.toUpperCase()))) {
			return false;
		}

		if (isAnOrganization == null) {
			log.error("Could not decide whether {} is an organization or not. Add appropriate entry in OrganizationNameFilter", organizationName);
		}

		return false;
	}
}

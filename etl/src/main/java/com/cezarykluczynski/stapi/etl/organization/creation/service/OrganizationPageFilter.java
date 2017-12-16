package com.cezarykluczynski.stapi.etl.organization.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrganizationPageFilter implements MediaWikiPageFilter {

	private final CategorySortingService categorySortingService;

	private final OrganizationNameFilter organizationNameFilter;

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
		OrganizationNameFilter.Match isAnOrganization = organizationNameFilter.isAnOrganization(organizationName);

		if (OrganizationNameFilter.Match.IS_NOT_AN_ORGANIZATION.equals(isAnOrganization)) {
			return true;
		}

		List<String> firstLetters = Lists.newArrayList(page.getTitle().split("(?<=[\\S])[\\S]*\\s*"));
		if (firstLetters.size() > 1 && firstLetters.stream().allMatch(letter -> letter.equals(letter.toUpperCase()))) {
			return false;
		}

		if (OrganizationNameFilter.Match.UNKNOWN_RESULT.equals(isAnOrganization)) {
			log.error("Could not decide whether \"{}\" is an organization or not. Add appropriate entry in OrganizationNameFilter", organizationName);
		}

		return false;
	}
}

package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategorySortingService {

	private static final String CATEGORY_PREFIX = "category:";

	private WikitextApi wikitextApi;

	@Inject
	public CategorySortingService(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	public boolean isSortedOnTopOfAnyCategory(Page page) {
		return getCategoriesThisPageIsSortedOnTopOf(page).count() > 0;
	}

	public boolean isSortedOnTopOfAnyOfCategories(Page page, List<String> categoryList) {
		List<String> categoryListLowerCase = categoryList.stream()
				.map(String::toLowerCase)
				.collect(Collectors.toList());

		return getCategoriesThisPageIsSortedOnTopOf(page)
				.map(PageLink::getTitle)
				.map(String::toLowerCase)
				.map(pageLinkTitle -> pageLinkTitle.substring(CATEGORY_PREFIX.length()))
				.anyMatch(categoryListLowerCase::contains);
	}

	private Stream<PageLink> getCategoriesThisPageIsSortedOnTopOf(Page page) {
		return wikitextApi.getPageLinksFromWikitext(page.getWikitext())
				.stream()
				.filter(pageLink -> pageLink.getTitle().toLowerCase().startsWith(CATEGORY_PREFIX))
				.filter(pageLink -> pageLink.getDescription() != null && pageLink.getDescription().length() == 0);
	}

}

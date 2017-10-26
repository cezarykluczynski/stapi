package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CategorySortingService {

	private static final String CATEGORY_PREFIX = "category:";
	private static final char SPACE = ' ';

	private final WikitextApi wikitextApi;

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
				.filter(pageLink -> isTopSorting(pageLink.getDescription(), pageLink.getUntrimmedDescription()));
	}

	private static boolean isTopSorting(String sorting, String untrimmedSorting) {
		return sorting != null && sorting.length() == 0 || untrimmedSorting != null && untrimmedSorting.charAt(0) == SPACE;
	}

}

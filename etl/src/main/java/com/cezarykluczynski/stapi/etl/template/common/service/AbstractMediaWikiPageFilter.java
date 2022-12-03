package com.cezarykluczynski.stapi.etl.template.common.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractMediaWikiPageFilter implements MediaWikiPageFilter {

	private final MediaWikiPageFilterConfiguration configuration;

	@Override
	@SuppressWarnings({"NPathComplexity", "ReturnCount"})
	public boolean shouldBeFilteredOut(Page page) {
		if (configuration.isSkipRedirects() && !page.getRedirectPath().isEmpty()) {
			return true;
		}

		String pageTitle = page.getTitle();
		final Collection<String> invalidTitles = configuration.getInvalidTitles();
		if (CollectionUtils.isNotEmpty(invalidTitles) && invalidTitles.contains(pageTitle)) {
			return true;
		}

		final Collection<String> invalidPrefixes = configuration.getInvalidPrefixes();
		if (CollectionUtils.isNotEmpty(invalidPrefixes) && StringUtil.startsWithAnyIgnoreCase(pageTitle, invalidPrefixes)) {
			return true;
		}

		final Collection<String> invalidSuffixes = configuration.getInvalidSuffixes();
		if (CollectionUtils.isNotEmpty(invalidSuffixes) && StringUtil.endsWithAnyIgnoreCase(pageTitle, invalidSuffixes)) {
			return true;
		}

		final Collection<String> invalidCategories = configuration.getInvalidCategories();
		if (CollectionUtils.isNotEmpty(invalidCategories)) {
			final List<String> categories = getCategoryTitlesExtractingProcessor().process(page.getCategories());
			if (CollectionUtils.isNotEmpty(categories) && CollectionUtils.containsAny(categories, invalidCategories)) {
				return true;
			}
		}

		if (configuration.isSkipPagesSortedOnTopOfAnyCategory() && getCategorySortingService().isSortedOnTopOfAnyCategory(page)) {
			return true;
		}

		final Collection<String> topCategoriesToSkip = configuration.getInvalidCategoriesToBeSortedOnTopOf();
		if (CollectionUtils.isNotEmpty(topCategoriesToSkip)
				&& getCategorySortingService().isSortedOnTopOfAnyOfCategories(page, topCategoriesToSkip)) {
			return true;
		}

		return false;
	}

	public CategorySortingService getCategorySortingService() {
		throw new RuntimeException("`getCategorySortingService` has to be implemented");
	}

	public CategoryTitlesExtractingProcessor getCategoryTitlesExtractingProcessor() {
		throw new RuntimeException("`getCategoryTitlesExtractingProcessor` has to be implemented");
	}

}

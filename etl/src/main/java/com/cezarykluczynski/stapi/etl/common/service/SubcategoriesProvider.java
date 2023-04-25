package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoriesProvider {

	private final CategoryApi categoryApi;

	private final Map<String, List<String>> subcategories = Maps.newLinkedHashMap();

	public synchronized List<String> provideSubcategories(String categoryTitle) {
		if (!subcategories.containsKey(categoryTitle)) {
			final List<CategoryHeader> categoriesInCategory = categoryApi
					.getCategoriesInCategoryIncludingSubcategories(categoryTitle, MediaWikiSource.MEMORY_ALPHA_EN, Lists.newArrayList());
			final List<String> categories = Lists.newArrayList(categoryTitle);
			categories.addAll(categoriesInCategory.stream()
					.map(CategoryHeader::getTitle)
					.collect(Collectors.toList()));
			subcategories.put(categoryTitle, categories);
		}
		return subcategories.get(categoryTitle);
	}

}

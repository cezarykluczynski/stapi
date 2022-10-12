package com.cezarykluczynski.stapi.etl.performer.creation.service;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformerCategoriesProvider {

	private final CategoryApi categoryApi;

	private List<String> performerCategories;

	public synchronized List<String> provide() {
		if (performerCategories == null) {
			final List<CategoryHeader> categoriesInCategory = categoryApi
					.getCategoriesInCategoryIncludingSubcategories(CategoryTitle.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN);
			final List<String> categories = Lists.newArrayList(CategoryTitle.PERFORMERS);
			categories.addAll(categoriesInCategory.stream()
					.map(CategoryHeader::getTitle)
					.collect(Collectors.toList()));
			performerCategories = categories;
		}
		return Lists.newArrayList(performerCategories);
	}

}

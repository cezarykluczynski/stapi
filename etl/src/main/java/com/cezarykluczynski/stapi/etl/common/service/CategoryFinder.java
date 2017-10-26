package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CategoryFinder {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public CategoryFinder(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	public boolean hasAnyCategory(Page page, Collection<String> categoryCollection) {
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(page.getCategories());
		return categoryTitleList.stream().anyMatch(categoryCollection::contains);
	}

}

package com.cezarykluczynski.stapi.etl.comics.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ComicsPageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.COMICS, PageTitle.PHOTONOVELS);

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public ComicsPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public boolean shouldBeFilteredOut(Page item) {
		return INVALID_TITLES.contains(item.getTitle()) || categoryTitlesExtractingProcessor.process(item.getCategories())
				.contains(CategoryTitle.STAR_TREK_SERIES_MAGAZINES) || !item.getRedirectPath().isEmpty();
	}

}

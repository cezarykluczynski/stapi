package com.cezarykluczynski.stapi.etl.comics.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ComicsPageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.COMICS, PageTitle.PHOTONOVELS, "Trial By Fire" /* unpublished */);
	private static final Set<String> INVALID_CATEGORIES = Sets.newHashSet(CategoryTitle.STAR_TREK_SERIES_MAGAZINES);

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public ComicsPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategories(INVALID_CATEGORIES)
				.build());
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;

	}

}

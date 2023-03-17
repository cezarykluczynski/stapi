package com.cezarykluczynski.stapi.etl.location.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LocationPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_LOCATIONS = Lists.newArrayList("Baldwin", "Door", "Geography", "San Francisco locations", "Flood",
			"Address");
	private static final List<String> INVALID_CATEGORIES = Lists.newArrayList(CategoryTitle.LISTS);

	static {
		INVALID_CATEGORIES.addAll(CategoryTitles.ORGANIZATIONS);
	}

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public LocationPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_LOCATIONS)
				.invalidCategories(INVALID_CATEGORIES)
				.build());
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

}

package com.cezarykluczynski.stapi.etl.astronomical_object.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstronomicalObjectPageFilter implements MediaWikiPageFilter {

	private static final String PLANETARY_CLASSIFICATION = "Planetary classification";
	private static final String UNNAMED_PREFIX = "Unnamed";

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public AstronomicalObjectPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (!page.getRedirectPath().isEmpty()) {
			return true;
		}

		String title = page.getTitle();
		if (title.startsWith(UNNAMED_PREFIX) || PLANETARY_CLASSIFICATION.equals(title)) {
			return true;
		}

		List<String> categoryHeaderList = categoryTitlesExtractingProcessor.process(page.getCategories());
		return categoryHeaderList.contains(CategoryTitle.PLANET_LISTS);
	}

}

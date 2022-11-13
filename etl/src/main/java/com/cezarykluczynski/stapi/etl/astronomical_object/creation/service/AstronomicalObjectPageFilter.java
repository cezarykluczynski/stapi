package com.cezarykluczynski.stapi.etl.astronomical_object.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstronomicalObjectPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Planetary classification", "Americas", "Bohemia", "Boundary layer",
			"Breeding planet", "Earth Sector 2", "Earth Sector 45", "Earth Sector 49", "Frontier", "Galaxy", "Grid square fifteen-delta",
			"Grid square twelve-delta", "Homeworld", "Interstellar dust cloud", "Jongrinsb Theater", "Jospania Theater", "Mekro'vak region",
			"Nebula", "Niwray Border", "Patrol area", "Quasar", "Temporal inversion fold", "Territory", "Time zone", "Tropics", "Zone");
	private static final List<String> INVALID_CATEGORIES = Lists.newArrayList(CategoryTitle.PLANET_LISTS, CategoryTitle.CELESTIAL_OBJECTS,
			CategoryTitle.ASTRONOMICAL_CLASSIFICATIONS);
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
		if (title.startsWith(UNNAMED_PREFIX) || INVALID_TITLES.contains(title)) {
			return true;
		}

		List<String> categoryHeaderList = categoryTitlesExtractingProcessor.process(page.getCategories());
		return CollectionUtils.containsAny(categoryHeaderList, INVALID_CATEGORIES);
	}

}

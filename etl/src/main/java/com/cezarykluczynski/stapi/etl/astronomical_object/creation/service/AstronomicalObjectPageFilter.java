package com.cezarykluczynski.stapi.etl.astronomical_object.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstronomicalObjectPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Planetary classification", "Americas", "Bohemia", "Boundary layer",
			"Breeding planet", "Earth Sector 2", "Earth Sector 45", "Earth Sector 49", "Frontier", "Galaxy", "Grid square fifteen-delta",
			"Grid square twelve-delta", "Homeworld", "Interstellar dust cloud", "Jongrinsb Theater", "Jospania Theater", "Mekro'vak region",
			"Nebula", "Niwray Border", "Patrol area", "Quasar", "Temporal inversion fold", "Territory", "Time zone", "Tropics", "Zone",
			"Borg spatial designations");
	private static final List<String> INVALID_CATEGORIES = Lists.newArrayList(CategoryTitle.PLANET_LISTS, CategoryTitle.CELESTIAL_OBJECTS,
			CategoryTitle.ASTRONOMICAL_CLASSIFICATIONS);
	private static final List<String> INVALID_PREFIXES = Lists.newArrayList("Unnamed");

	@Getter
	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public AstronomicalObjectPageFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategories(INVALID_CATEGORIES)
				.invalidPrefixes(INVALID_PREFIXES)
				.build());
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

}

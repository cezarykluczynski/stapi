package com.cezarykluczynski.stapi.etl.food.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Earth foods and beverages", "Dessert", "Sauce", "Snack", "Vintage");
	private static final List<String> INVALID_PREFIXES = Lists.newArrayList("Unnamed ");
	private static final List<String> INVALID_SUFFIXES = Lists.newArrayList(" food", "  cuisine");

	public FoodPageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidPrefixes(INVALID_PREFIXES)
				.invalidSuffixes(INVALID_SUFFIXES)
				.build());
	}

}

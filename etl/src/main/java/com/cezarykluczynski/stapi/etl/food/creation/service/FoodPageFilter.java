package com.cezarykluczynski.stapi.etl.food.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPageFilter implements MediaWikiPageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed ";
	private static final List<String> NOT_FOODS = Lists.newArrayList("Earth foods and beverages", "Dessert","Sauce", "Snack", "Vintage");
	private static final List<String> INVALID_SUFFIXES = Lists.newArrayList(" food", "  cuisine");

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		String pageTitle = page.getTitle();
		return !page.getRedirectPath().isEmpty() || pageTitle.startsWith(UNNAMED_PREFIX) || NOT_FOODS.contains(pageTitle)
				|| StringUtil.endsWithAny(pageTitle, INVALID_SUFFIXES);
	}

}

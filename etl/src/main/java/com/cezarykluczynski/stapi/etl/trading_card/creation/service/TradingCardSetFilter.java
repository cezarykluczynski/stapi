package com.cezarykluczynski.stapi.etl.trading_card.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.WordPressPageFilter;
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradingCardSetFilter implements WordPressPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Contact Form");

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return INVALID_TITLES.contains(page.getRenderedTitle());
	}

}

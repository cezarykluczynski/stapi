package com.cezarykluczynski.stapi.etl.trading_card.creation.service;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradingCardTableFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("No Details Available", "Other Items");

	public boolean isNonCardTable(String tableHeader) {
		return INVALID_TITLES.contains(tableHeader);
	}

}

package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradingCardSetCountiesProcessor implements ItemProcessor<String, Set<Country>> {

	@Override
	public Set<Country> process(String item) throws Exception {
		Set<Country> countrySet = Sets.newHashSet();
		// TODO
		return countrySet;
	}

}

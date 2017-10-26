package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.country.service.CountryDTOProvider;
import com.cezarykluczynski.stapi.etl.country.service.CountryFactory;
import com.cezarykluczynski.stapi.model.country.entity.Country;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TradingCardSetCountiesProcessor implements ItemProcessor<String, Set<Country>> {

	private final CountryDTOProvider countryDTOProvider;

	private final CountryFactory countryFactory;

	public TradingCardSetCountiesProcessor(CountryDTOProvider countryDTOProvider, CountryFactory countryFactory) {
		this.countryDTOProvider = countryDTOProvider;
		this.countryFactory = countryFactory;
	}

	@Override
	public synchronized Set<Country> process(String item) throws Exception {
		return countryDTOProvider.provideFromString(item)
				.stream()
				.map(countryFactory::create)
				.collect(Collectors.toSet());
	}

}

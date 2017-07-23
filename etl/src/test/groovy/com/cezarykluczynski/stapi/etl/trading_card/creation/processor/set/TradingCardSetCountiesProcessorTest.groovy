package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.etl.country.dto.CountryDTO
import com.cezarykluczynski.stapi.etl.country.service.CountryDTOProvider
import com.cezarykluczynski.stapi.etl.country.service.CountryFactory
import com.cezarykluczynski.stapi.model.country.entity.Country
import com.google.common.collect.Sets
import spock.lang.Specification

class TradingCardSetCountiesProcessorTest extends Specification {

	private static final String COUNTRIES = 'COUNTRIES'

	private CountryDTOProvider countryDTOProviderMock

	private CountryFactory countryFactoryMock

	private TradingCardSetCountiesProcessor tradingCardSetCountiesProcessor

	void setup() {
		countryDTOProviderMock = Mock()
		countryFactoryMock = Mock()
		tradingCardSetCountiesProcessor = new TradingCardSetCountiesProcessor(countryDTOProviderMock, countryFactoryMock)
	}

	void "passes every CountryDTO returned by provider to CountryFactory"() {
		given:
		CountryDTO countryDTO1 = Mock()
		CountryDTO countryDTO2 = Mock()
		Country country1 = Mock()
		Country country2 = Mock()

		when:
		Set<Country> countrySet = tradingCardSetCountiesProcessor.process(COUNTRIES)

		then:
		1 * countryDTOProviderMock.provideFromString(COUNTRIES) >> Sets.newHashSet(countryDTO1, countryDTO2)
		1 * countryFactoryMock.create(countryDTO1) >> country1
		1 * countryFactoryMock.create(countryDTO2) >> country2
		0 * _
		countrySet.size() == 2
		countrySet.contains country1
		countrySet.contains country2
	}

}

package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetTableHeader
import com.cezarykluczynski.stapi.model.country.entity.Country
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.google.common.collect.Sets
import spock.lang.Specification

class TradingCardSetTableValuesEnrichingProcessorTest extends Specification {

	private static final String CARDS_SIZE = 'CARDS_SIZE'
	private static final String COUNTRY = 'COUNTRY'
	private static final double WIDTH = 3.5d
	private static final double HEIGHT = 2.25d

	private CardSizeProcessor cardSizeProcessorMock

	private TradingCardSetCountiesProcessor tradingCardSetCountiesProcessorMock

	private TradingCardSetTableValuesEnrichingProcessor tradingCardSetTableValuesEnrichingProcessor

	void setup() {
		cardSizeProcessorMock = Mock()
		tradingCardSetCountiesProcessorMock = Mock()
		tradingCardSetTableValuesEnrichingProcessor = new TradingCardSetTableValuesEnrichingProcessor(cardSizeProcessorMock,
				tradingCardSetCountiesProcessorMock)
	}

	void "sets card with and card height from CardSizeProcessor when it returns CardSizeDTO when cards size part was found"() {
		given:
		TradingCarSetHeaderValuePair tradingCarSetHeaderValuePair = new TradingCarSetHeaderValuePair(
				headerText: TradingCardSetTableHeader.CARDS_SIZE,
				valueText: CARDS_SIZE)
		TradingCardSet tradingCardSet = new TradingCardSet()
		CardSizeDTO cardSizeDTO = new CardSizeDTO(
				width: WIDTH,
				height: HEIGHT)

		when:
		tradingCardSetTableValuesEnrichingProcessor.enrich(EnrichablePair.of(tradingCarSetHeaderValuePair, tradingCardSet))

		then:
		1 * cardSizeProcessorMock.process(CARDS_SIZE) >> cardSizeDTO
		0 * _
		tradingCardSet.cardWidth == WIDTH
		tradingCardSet.cardHeight == HEIGHT
	}

	void "when CardSizeProcessor returns null, nothing happens"() {
		given:
		TradingCarSetHeaderValuePair tradingCarSetHeaderValuePair = new TradingCarSetHeaderValuePair(
				headerText: TradingCardSetTableHeader.CARDS_SIZE,
				valueText: CARDS_SIZE)
		TradingCardSet tradingCardSet = new TradingCardSet()

		when:
		tradingCardSetTableValuesEnrichingProcessor.enrich(EnrichablePair.of(tradingCarSetHeaderValuePair, tradingCardSet))

		then:
		1 * cardSizeProcessorMock.process(CARDS_SIZE) >> null
		0 * _
		tradingCardSet.cardWidth == null
		tradingCardSet.cardHeight == null
	}

	void "add all countries from TradingCardSetCountiesProcessor when country part was found"() {
		given:
		TradingCarSetHeaderValuePair tradingCarSetHeaderValuePair = new TradingCarSetHeaderValuePair(
				headerText: TradingCardSetTableHeader.COUNTRY,
				valueText: COUNTRY)
		TradingCardSet tradingCardSet = new TradingCardSet()
		Country country1 = Mock()
		Country country2 = Mock()

		when:
		tradingCardSetTableValuesEnrichingProcessor.enrich(EnrichablePair.of(tradingCarSetHeaderValuePair, tradingCardSet))

		then:
		1 * tradingCardSetCountiesProcessorMock.process(COUNTRY) >> Sets.newHashSet(country1, country2)
		0 * _
		tradingCardSet.countriesOfOrigin.size() == 2
		tradingCardSet.countriesOfOrigin.contains country1
		tradingCardSet.countriesOfOrigin.contains country2
	}

}

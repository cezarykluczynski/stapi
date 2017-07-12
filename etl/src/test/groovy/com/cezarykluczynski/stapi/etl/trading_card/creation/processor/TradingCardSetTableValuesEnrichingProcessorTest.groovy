package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetTableHeader
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import spock.lang.Specification

class TradingCardSetTableValuesEnrichingProcessorTest extends Specification {

	private static final String CARDS_SIZE = 'CARDS_SIZE'
	private static final double WIDTH = 3.5d
	private static final double HEIGHT = 2.25d

	private CardSizeProcessor cardSizeProcessorMock

	private TradingCardSetTableValuesEnrichingProcessor tradingCardSetTableValuesEnrichingProcessor

	void setup() {
		cardSizeProcessorMock = Mock()
		tradingCardSetTableValuesEnrichingProcessor = new TradingCardSetTableValuesEnrichingProcessor(cardSizeProcessorMock)
	}

	void "passes ComicsTemplate to ComicsTemplatePartStaffEnrichingProcessor when writer part is found"() {
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

	void "passes ComicsTemplatePartStaffEnrichingProcessor returns null, nothing happens"() {
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

}

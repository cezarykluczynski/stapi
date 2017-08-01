package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFull
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import org.mapstruct.factory.Mappers

class TradingCardDeckFullRestMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckFullRestMapper tradingCardDeckFullRestMapper

	void setup() {
		tradingCardDeckFullRestMapper = Mappers.getMapper(TradingCardDeckFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		TradingCardDeck tradingCardDeck = createTradingCardDeck()

		when:
		TradingCardDeckFull tradingCardDeckFull = tradingCardDeckFullRestMapper.mapFull(tradingCardDeck)

		then:
		tradingCardDeckFull.uid == UID
		tradingCardDeckFull.name == NAME
		tradingCardDeckFull.frequency == FREQUENCY
		tradingCardDeckFull.tradingCardSet != null
		tradingCardDeckFull.tradingCards.size() == tradingCardDeck.tradingCards.size()
	}

}

package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckHeader
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardDeckHeaderRestMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckHeaderRestMapper tradingCardDeckHeaderRestMapper

	void setup() {
		tradingCardDeckHeaderRestMapper = Mappers.getMapper(TradingCardDeckHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		TradingCardDeck tradingCardDeck = new TradingCardDeck(
				uid: UID,
				name: NAME)

		when:
		TradingCardDeckHeader tradingCardDeckHeader = tradingCardDeckHeaderRestMapper.map(Lists.newArrayList(tradingCardDeck))[0]

		then:
		tradingCardDeckHeader.uid == UID
		tradingCardDeckHeader.name == NAME
	}

}

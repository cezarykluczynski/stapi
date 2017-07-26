package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckHeader
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardDeckHeaderSoapMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckHeaderSoapMapper tradingCardDeckHeaderSoapMapper

	void setup() {
		tradingCardDeckHeaderSoapMapper = Mappers.getMapper(TradingCardDeckHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		TradingCardDeck tradingCardDeck = new TradingCardDeck(
				uid: UID,
				name: NAME)

		when:
		TradingCardDeckHeader tradingCardDeckHeader = tradingCardDeckHeaderSoapMapper.map(Lists.newArrayList(tradingCardDeck))[0]

		then:
		tradingCardDeckHeader.uid == UID
		tradingCardDeckHeader.name == NAME
	}

}

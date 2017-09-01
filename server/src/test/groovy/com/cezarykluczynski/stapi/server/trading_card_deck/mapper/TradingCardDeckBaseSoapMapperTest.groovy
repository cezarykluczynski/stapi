package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBase as TradingCardDeckBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardDeckBaseSoapMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper

	void setup() {
		tradingCardDeckBaseSoapMapper = Mappers.getMapper(TradingCardDeckBaseSoapMapper)
	}

	void "maps SOAP TradingCardDeckRequest to TradingCardDeckRequestDTO"() {

		given:
		TradingCardDeckBaseRequest tradingCardDeckBaseRequest = new TradingCardDeckBaseRequest(
				name: NAME,
				tradingCardSetUid: TRADING_CARD_SET_UID)

		when:
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckBaseSoapMapper.mapBase tradingCardDeckBaseRequest

		then:
		tradingCardDeckRequestDTO.name == NAME
		tradingCardDeckRequestDTO.tradingCardSetUid == TRADING_CARD_SET_UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		TradingCardDeck tradingCardDeck = createTradingCardDeck()

		when:
		TradingCardDeckBase tradingCardDeckBase = tradingCardDeckBaseSoapMapper.mapBase(Lists.newArrayList(tradingCardDeck))[0]

		then:
		tradingCardDeckBase.uid == UID
		tradingCardDeckBase.name == NAME
		tradingCardDeckBase.frequency == FREQUENCY
		tradingCardDeckBase.tradingCardSet != null
	}

}

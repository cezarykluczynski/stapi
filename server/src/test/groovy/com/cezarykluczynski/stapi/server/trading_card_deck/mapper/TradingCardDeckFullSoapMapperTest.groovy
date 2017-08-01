package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import org.mapstruct.factory.Mappers

class TradingCardDeckFullSoapMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper

	void setup() {
		tradingCardDeckFullSoapMapper = Mappers.getMapper(TradingCardDeckFullSoapMapper)
	}

	void "maps SOAP TradingCardDeckFullRequest to TradingCardDeckBaseRequestDTO"() {
		given:
		TradingCardDeckFullRequest tradingCardDeckFullRequest = new TradingCardDeckFullRequest(uid: UID)

		when:
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckFullSoapMapper.mapFull tradingCardDeckFullRequest

		then:
		tradingCardDeckRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		TradingCardDeck tradingCardDeck = createTradingCardDeck()

		when:
		TradingCardDeckFull tradingCardDeckFull = tradingCardDeckFullSoapMapper.mapFull(tradingCardDeck)

		then:
		tradingCardDeckFull.uid == UID
		tradingCardDeckFull.name == NAME
		tradingCardDeckFull.frequency == FREQUENCY
		tradingCardDeckFull.tradingCardSet != null
		tradingCardDeckFull.tradingCards.size() == tradingCardDeck.tradingCards.size()
	}

}

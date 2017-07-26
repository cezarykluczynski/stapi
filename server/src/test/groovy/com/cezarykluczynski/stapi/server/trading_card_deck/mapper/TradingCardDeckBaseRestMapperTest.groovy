package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBase
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardDeckBaseRestMapperTest extends AbstractTradingCardDeckMapperTest {

	private TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper

	void setup() {
		tradingCardDeckBaseRestMapper = Mappers.getMapper(TradingCardDeckBaseRestMapper)
	}

	void "maps TradingCardDeckRestBeanParams to TradingCardDeckRequestDTO"() {
		given:
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = new TradingCardDeckRestBeanParams(
				uid: UID,
				name: NAME,
				tradingCardSetUid: TRADING_CARD_SET_UID)

		when:
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckBaseRestMapper.mapBase tradingCardDeckRestBeanParams

		then:
		tradingCardDeckRequestDTO.uid == UID
		tradingCardDeckRequestDTO.name == NAME
		tradingCardDeckRequestDTO.tradingCardSetUid == TRADING_CARD_SET_UID
	}

	void "maps DB entity to base REST entity"() {
		given:
		TradingCardDeck tradingCardDeck = createTradingCardDeck()

		when:
		TradingCardDeckBase tradingCardDeckBase = tradingCardDeckBaseRestMapper.mapBase(Lists.newArrayList(tradingCardDeck))[0]

		then:
		tradingCardDeckBase.uid == UID
		tradingCardDeckBase.name == NAME
		tradingCardDeckBase.frequency == FREQUENCY
		tradingCardDeckBase.tradingCardSet != null
	}

}

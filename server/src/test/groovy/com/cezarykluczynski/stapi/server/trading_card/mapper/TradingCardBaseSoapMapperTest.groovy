package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBase as TradingCardBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardBaseSoapMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardBaseSoapMapper tradingCardBaseSoapMapper

	void setup() {
		tradingCardBaseSoapMapper = Mappers.getMapper(TradingCardBaseSoapMapper)
	}

	void "maps SOAP TradingCardRequest to TradingCardRequestDTO"() {

		given:
		TradingCardBaseRequest tradingCardBaseRequest = new TradingCardBaseRequest(
				name: NAME,
				tradingCardDeckUid: TRADING_CARD_DECK_UID,
				tradingCardSetUid: TRADING_CARD_SET_UID)

		when:
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardBaseSoapMapper.mapBase tradingCardBaseRequest

		then:
		tradingCardRequestDTO.name == NAME
		tradingCardRequestDTO.tradingCardDeckUid == TRADING_CARD_DECK_UID
		tradingCardRequestDTO.tradingCardSetUid == TRADING_CARD_SET_UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		TradingCard tradingCard = createTradingCard()

		when:
		TradingCardBase tradingCardBase = tradingCardBaseSoapMapper.mapBase(Lists.newArrayList(tradingCard))[0]

		then:
		tradingCardBase.uid == UID
		tradingCardBase.name == NAME
		tradingCardBase.number == NUMBER
		tradingCardBase.releaseYear == RELEASE_YEAR
		tradingCardBase.productionRun == PRODUCTION_RUN
		tradingCardBase.tradingCardSet != null
		tradingCardBase.tradingCardDeck != null
	}

}

package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBase
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardBaseRestMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardBaseRestMapper tradingCardBaseRestMapper

	void setup() {
		tradingCardBaseRestMapper = Mappers.getMapper(TradingCardBaseRestMapper)
	}

	void "maps TradingCardRestBeanParams to TradingCardRequestDTO"() {
		given:
		TradingCardRestBeanParams tradingCardRestBeanParams = new TradingCardRestBeanParams(
				uid: UID,
				name: NAME,
				tradingCardDeckUid: TRADING_CARD_DECK_UID,
				tradingCardSetUid: TRADING_CARD_SET_UID)

		when:
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardBaseRestMapper.mapBase tradingCardRestBeanParams

		then:
		tradingCardRequestDTO.uid == UID
		tradingCardRequestDTO.name == NAME
		tradingCardRequestDTO.tradingCardDeckUid == TRADING_CARD_DECK_UID
		tradingCardRequestDTO.tradingCardSetUid == TRADING_CARD_SET_UID
	}

	void "maps DB entity to base REST entity"() {
		given:
		TradingCard tradingCard = createTradingCard()

		when:
		TradingCardBase tradingCardBase = tradingCardBaseRestMapper.mapBase(Lists.newArrayList(tradingCard))[0]

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

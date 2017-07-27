package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFull
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import org.mapstruct.factory.Mappers

class TradingCardFullRestMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardFullRestMapper tradingCardFullRestMapper

	void setup() {
		tradingCardFullRestMapper = Mappers.getMapper(TradingCardFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		TradingCard tradingCard = createTradingCard()

		when:
		TradingCardFull tradingCardFull = tradingCardFullRestMapper.mapFull(tradingCard)

		then:
		tradingCardFull.uid == UID
		tradingCardFull.name == NAME
		tradingCardFull.number == NUMBER
		tradingCardFull.releaseYear == RELEASE_YEAR
		tradingCardFull.productionRun == PRODUCTION_RUN
		tradingCardFull.tradingCardSet != null
		tradingCardFull.tradingCardDeck != null
	}

}

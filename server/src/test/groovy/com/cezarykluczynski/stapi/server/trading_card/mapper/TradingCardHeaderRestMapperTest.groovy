package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardHeader
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardHeaderRestMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardHeaderRestMapper tradingCardHeaderRestMapper

	void setup() {
		tradingCardHeaderRestMapper = Mappers.getMapper(TradingCardHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		TradingCard tradingCard = new TradingCard(
				uid: UID,
				name: NAME)

		when:
		TradingCardHeader tradingCardHeader = tradingCardHeaderRestMapper.map(Lists.newArrayList(tradingCard))[0]

		then:
		tradingCardHeader.uid == UID
		tradingCardHeader.name == NAME
	}

}

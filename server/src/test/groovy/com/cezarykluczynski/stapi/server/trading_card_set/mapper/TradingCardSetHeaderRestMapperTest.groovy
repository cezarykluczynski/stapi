package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetHeader
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardSetHeaderRestMapperTest extends AbstractTradingCardSetMapperTest {

	private TradingCardSetHeaderRestMapper tradingCardSetHeaderRestMapper

	void setup() {
		tradingCardSetHeaderRestMapper = Mappers.getMapper(TradingCardSetHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		TradingCardSet tradingCardSet = new TradingCardSet(
				uid: UID,
				name: NAME)

		when:
		TradingCardSetHeader tradingCardSetHeader = tradingCardSetHeaderRestMapper.map(Lists.newArrayList(tradingCardSet))[0]

		then:
		tradingCardSetHeader.uid == UID
		tradingCardSetHeader.name == NAME
	}

}

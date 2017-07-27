package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardHeader
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardHeaderSoapMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardHeaderSoapMapper tradingCardHeaderSoapMapper

	void setup() {
		tradingCardHeaderSoapMapper = Mappers.getMapper(TradingCardHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		TradingCard tradingCard = new TradingCard(
				uid: UID,
				name: NAME)

		when:
		TradingCardHeader tradingCardHeader = tradingCardHeaderSoapMapper.map(Lists.newArrayList(tradingCard))[0]

		then:
		tradingCardHeader.uid == UID
		tradingCardHeader.name == NAME
	}

}

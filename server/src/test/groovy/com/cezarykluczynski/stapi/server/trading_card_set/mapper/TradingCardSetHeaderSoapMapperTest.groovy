package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetHeader
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardSetHeaderSoapMapperTest extends AbstractTradingCardSetMapperTest {

	private TradingCardSetHeaderSoapMapper tradingCardSetHeaderSoapMapper

	void setup() {
		tradingCardSetHeaderSoapMapper = Mappers.getMapper(TradingCardSetHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		TradingCardSet tradingCardSet = new TradingCardSet(
				uid: UID,
				name: NAME)

		when:
		TradingCardSetHeader tradingCardSetHeader = tradingCardSetHeaderSoapMapper.map(Lists.newArrayList(tradingCardSet))[0]

		then:
		tradingCardSetHeader.uid == UID
		tradingCardSetHeader.name == NAME
	}

}

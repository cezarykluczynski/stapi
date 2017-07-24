package com.cezarykluczynski.stapi.server.trading_card_set.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.server.trading_card_set.reader.TradingCardSetSoapReader
import spock.lang.Specification

class TradingCardSetSoapEndpointTest extends Specification {

	private TradingCardSetSoapReader tradingCardSetSoapReaderMock

	private TradingCardSetSoapEndpoint tradingCardSetSoapEndpoint

	void setup() {
		tradingCardSetSoapReaderMock = Mock()
		tradingCardSetSoapEndpoint = new TradingCardSetSoapEndpoint(tradingCardSetSoapReaderMock)
	}

	void "passes base call to TradingCardSetSoapReader"() {
		given:
		TradingCardSetBaseRequest tradingCardSetBaseRequest = Mock()
		TradingCardSetBaseResponse tradingCardSetBaseResponse = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetResponseResult = tradingCardSetSoapEndpoint.getTradingCardSetBase(tradingCardSetBaseRequest)

		then:
		1 * tradingCardSetSoapReaderMock.readBase(tradingCardSetBaseRequest) >> tradingCardSetBaseResponse
		tradingCardSetResponseResult == tradingCardSetBaseResponse
	}

	void "passes full call to TradingCardSetSoapReader"() {
		given:
		TradingCardSetFullRequest tradingCardSetFullRequest = Mock()
		TradingCardSetFullResponse tradingCardSetFullResponse = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetResponseResult = tradingCardSetSoapEndpoint.getTradingCardSetFull(tradingCardSetFullRequest)

		then:
		1 * tradingCardSetSoapReaderMock.readFull(tradingCardSetFullRequest) >> tradingCardSetFullResponse
		tradingCardSetResponseResult == tradingCardSetFullResponse
	}

}

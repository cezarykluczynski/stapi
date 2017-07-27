package com.cezarykluczynski.stapi.server.trading_card.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse
import com.cezarykluczynski.stapi.server.trading_card.reader.TradingCardSoapReader
import spock.lang.Specification

class TradingCardSoapEndpointTest extends Specification {

	private TradingCardSoapReader tradingCardSoapReaderMock

	private TradingCardSoapEndpoint tradingCardSoapEndpoint

	void setup() {
		tradingCardSoapReaderMock = Mock()
		tradingCardSoapEndpoint = new TradingCardSoapEndpoint(tradingCardSoapReaderMock)
	}

	void "passes base call to TradingCardSoapReader"() {
		given:
		TradingCardBaseRequest tradingCardBaseRequest = Mock()
		TradingCardBaseResponse tradingCardBaseResponse = Mock()

		when:
		TradingCardBaseResponse tradingCardResponseResult = tradingCardSoapEndpoint.getTradingCardBase(tradingCardBaseRequest)

		then:
		1 * tradingCardSoapReaderMock.readBase(tradingCardBaseRequest) >> tradingCardBaseResponse
		tradingCardResponseResult == tradingCardBaseResponse
	}

	void "passes full call to TradingCardSoapReader"() {
		given:
		TradingCardFullRequest tradingCardFullRequest = Mock()
		TradingCardFullResponse tradingCardFullResponse = Mock()

		when:
		TradingCardFullResponse tradingCardResponseResult = tradingCardSoapEndpoint.getTradingCardFull(tradingCardFullRequest)

		then:
		1 * tradingCardSoapReaderMock.readFull(tradingCardFullRequest) >> tradingCardFullResponse
		tradingCardResponseResult == tradingCardFullResponse
	}

}

package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.server.trading_card_deck.reader.TradingCardDeckSoapReader
import spock.lang.Specification

class TradingCardDeckSoapEndpointTest extends Specification {

	private TradingCardDeckSoapReader tradingCardDeckSoapReaderMock

	private TradingCardDeckSoapEndpoint tradingCardDeckSoapEndpoint

	void setup() {
		tradingCardDeckSoapReaderMock = Mock()
		tradingCardDeckSoapEndpoint = new TradingCardDeckSoapEndpoint(tradingCardDeckSoapReaderMock)
	}

	void "passes base call to TradingCardDeckSoapReader"() {
		given:
		TradingCardDeckBaseRequest tradingCardDeckBaseRequest = Mock()
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckResponseResult = tradingCardDeckSoapEndpoint.getTradingCardDeckBase(tradingCardDeckBaseRequest)

		then:
		1 * tradingCardDeckSoapReaderMock.readBase(tradingCardDeckBaseRequest) >> tradingCardDeckBaseResponse
		tradingCardDeckResponseResult == tradingCardDeckBaseResponse
	}

	void "passes full call to TradingCardDeckSoapReader"() {
		given:
		TradingCardDeckFullRequest tradingCardDeckFullRequest = Mock()
		TradingCardDeckFullResponse tradingCardDeckFullResponse = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckResponseResult = tradingCardDeckSoapEndpoint.getTradingCardDeckFull(tradingCardDeckFullRequest)

		then:
		1 * tradingCardDeckSoapReaderMock.readFull(tradingCardDeckFullRequest) >> tradingCardDeckFullResponse
		tradingCardDeckResponseResult == tradingCardDeckFullResponse
	}

}

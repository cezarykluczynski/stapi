package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType
import spock.lang.Specification

class TradingCardDeckTest extends Specification {

	private TradingCardDeckPortType tradingCardDeckPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private TradingCardDeck tradingCardDeck

	void setup() {
		tradingCardDeckPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		tradingCardDeck = new TradingCardDeck(tradingCardDeckPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		TradingCardDeckBaseRequest tradingCardDeckBaseRequest = Mock()
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponseOutput = tradingCardDeck.search(tradingCardDeckBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardDeckBaseRequest)
		1 * tradingCardDeckPortTypeMock.getTradingCardDeckBase(tradingCardDeckBaseRequest) >> tradingCardDeckBaseResponse
		0 * _
		tradingCardDeckBaseResponse == tradingCardDeckBaseResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardDeckFullRequest tradingCardDeckFullRequest = Mock()
		TradingCardDeckFullResponse tradingCardDeckFullResponse = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponseOutput = tradingCardDeck.get(tradingCardDeckFullRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardDeckFullRequest)
		1 * tradingCardDeckPortTypeMock.getTradingCardDeckFull(tradingCardDeckFullRequest) >> tradingCardDeckFullResponse
		0 * _
		tradingCardDeckFullResponse == tradingCardDeckFullResponseOutput
	}

}

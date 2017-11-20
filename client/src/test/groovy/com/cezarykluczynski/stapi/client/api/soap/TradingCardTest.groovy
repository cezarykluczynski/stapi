package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType
import spock.lang.Specification

class TradingCardTest extends Specification {

	private TradingCardPortType tradingCardPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private TradingCard tradingCard

	void setup() {
		tradingCardPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		tradingCard = new TradingCard(tradingCardPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		TradingCardBaseRequest tradingCardBaseRequest = Mock()
		TradingCardBaseResponse tradingCardBaseResponse = Mock()

		when:
		TradingCardBaseResponse tradingCardBaseResponseOutput = tradingCard.search(tradingCardBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardBaseRequest)
		1 * tradingCardPortTypeMock.getTradingCardBase(tradingCardBaseRequest) >> tradingCardBaseResponse
		0 * _
		tradingCardBaseResponse == tradingCardBaseResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardFullRequest tradingCardFullRequest = Mock()
		TradingCardFullResponse tradingCardFullResponse = Mock()

		when:
		TradingCardFullResponse tradingCardFullResponseOutput = tradingCard.get(tradingCardFullRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardFullRequest)
		1 * tradingCardPortTypeMock.getTradingCardFull(tradingCardFullRequest) >> tradingCardFullResponse
		0 * _
		tradingCardFullResponse == tradingCardFullResponseOutput
	}

}

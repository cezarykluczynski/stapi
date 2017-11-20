package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType
import spock.lang.Specification

class TradingCardSetTest extends Specification {

	private TradingCardSetPortType tradingCardSetPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private TradingCardSet tradingCardSet

	void setup() {
		tradingCardSetPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		tradingCardSet = new TradingCardSet(tradingCardSetPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		TradingCardSetBaseRequest tradingCardSetBaseRequest = Mock()
		TradingCardSetBaseResponse tradingCardSetBaseResponse = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponseOutput = tradingCardSet.search(tradingCardSetBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardSetBaseRequest)
		1 * tradingCardSetPortTypeMock.getTradingCardSetBase(tradingCardSetBaseRequest) >> tradingCardSetBaseResponse
		0 * _
		tradingCardSetBaseResponse == tradingCardSetBaseResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardSetFullRequest tradingCardSetFullRequest = Mock()
		TradingCardSetFullResponse tradingCardSetFullResponse = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetFullResponseOutput = tradingCardSet.get(tradingCardSetFullRequest)

		then:
		1 * apiKeySupplierMock.supply(tradingCardSetFullRequest)
		1 * tradingCardSetPortTypeMock.getTradingCardSetFull(tradingCardSetFullRequest) >> tradingCardSetFullResponse
		0 * _
		tradingCardSetFullResponse == tradingCardSetFullResponseOutput
	}

}

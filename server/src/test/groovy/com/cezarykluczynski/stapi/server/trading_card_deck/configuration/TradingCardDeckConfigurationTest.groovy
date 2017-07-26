package com.cezarykluczynski.stapi.server.trading_card_deck.configuration

import com.cezarykluczynski.stapi.server.trading_card_deck.endpoint.TradingCardDeckRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.trading_card_deck.endpoint.TradingCardDeckSoapEndpoint
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullRestMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class TradingCardDeckConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TradingCardDeckConfiguration tradingCardDeckConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		tradingCardDeckConfiguration = new TradingCardDeckConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "TradingCardDeck SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = tradingCardDeckConfiguration.tradingCardDeckEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(TradingCardDeckSoapEndpoint, TradingCardDeckSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "TradingCardDeck REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = tradingCardDeckConfiguration.tradingCardDeckServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TradingCardDeckRestEndpoint, TradingCardDeckRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "TradingCardDeckBaseSoapMapper is created"() {
		when:
		TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper = tradingCardDeckConfiguration.tradingCardDeckBaseSoapMapper()

		then:
		tradingCardDeckBaseSoapMapper != null
	}

	void "TradingCardDeckFullSoapMapper is created"() {
		when:
		TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper = tradingCardDeckConfiguration.tradingCardDeckFullSoapMapper()

		then:
		tradingCardDeckFullSoapMapper != null
	}

	void "TradingCardDeckBaseRestMapper is created"() {
		when:
		TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper = tradingCardDeckConfiguration.tradingCardDeckBaseRestMapper()

		then:
		tradingCardDeckBaseRestMapper != null
	}

	void "TradingCardDeckFullRestMapper is created"() {
		when:
		TradingCardDeckFullRestMapper tradingCardDeckFullRestMapper = tradingCardDeckConfiguration.tradingCardDeckFullRestMapper()

		then:
		tradingCardDeckFullRestMapper != null
	}

}

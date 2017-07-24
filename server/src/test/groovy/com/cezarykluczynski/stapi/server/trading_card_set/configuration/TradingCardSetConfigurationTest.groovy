package com.cezarykluczynski.stapi.server.trading_card_set.configuration

import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetSoapEndpoint
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class TradingCardSetConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TradingCardSetConfiguration tradingCardSetConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		tradingCardSetConfiguration = new TradingCardSetConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "TradingCardSet SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = tradingCardSetConfiguration.tradingCardSetEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(TradingCardSetSoapEndpoint, TradingCardSetSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "TradingCardSet REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = tradingCardSetConfiguration.tradingCardSetServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TradingCardSetRestEndpoint, TradingCardSetRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "TradingCardSetBaseSoapMapper is created"() {
		when:
		TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper = tradingCardSetConfiguration.tradingCardSetBaseSoapMapper()

		then:
		tradingCardSetBaseSoapMapper != null
	}

	void "TradingCardSetFullSoapMapper is created"() {
		when:
		TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper = tradingCardSetConfiguration.tradingCardSetFullSoapMapper()

		then:
		tradingCardSetFullSoapMapper != null
	}

	void "TradingCardSetBaseRestMapper is created"() {
		when:
		TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper = tradingCardSetConfiguration.tradingCardSetBaseRestMapper()

		then:
		tradingCardSetBaseRestMapper != null
	}

	void "TradingCardSetFullRestMapper is created"() {
		when:
		TradingCardSetFullRestMapper tradingCardSetFullRestMapper = tradingCardSetConfiguration.tradingCardSetFullRestMapper()

		then:
		tradingCardSetFullRestMapper != null
	}

}

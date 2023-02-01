package com.cezarykluczynski.stapi.server.trading_card.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.trading_card.endpoint.TradingCardRestEndpoint
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class TradingCardConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TradingCardConfiguration tradingCardConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		tradingCardConfiguration = new TradingCardConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "TradingCard REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = tradingCardConfiguration.tradingCardServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TradingCardRestEndpoint, TradingCardRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "TradingCardBaseRestMapper is created"() {
		when:
		TradingCardBaseRestMapper tradingCardBaseRestMapper = tradingCardConfiguration.tradingCardBaseRestMapper()

		then:
		tradingCardBaseRestMapper != null
	}

	void "TradingCardFullRestMapper is created"() {
		when:
		TradingCardFullRestMapper tradingCardFullRestMapper = tradingCardConfiguration.tradingCardFullRestMapper()

		then:
		tradingCardFullRestMapper != null
	}

}

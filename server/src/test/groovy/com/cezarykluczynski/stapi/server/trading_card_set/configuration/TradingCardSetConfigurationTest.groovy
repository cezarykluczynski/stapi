package com.cezarykluczynski.stapi.server.trading_card_set.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetRestEndpoint
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class TradingCardSetConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TradingCardSetConfiguration tradingCardSetConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		tradingCardSetConfiguration = new TradingCardSetConfiguration(endpointFactory: endpointFactoryMock)
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

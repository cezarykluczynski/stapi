package com.cezarykluczynski.stapi.server.trading_card.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.trading_card.endpoint.TradingCardRestEndpoint;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradingCardConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server tradingCardServer() {
		return endpointFactory.createRestEndpoint(TradingCardRestEndpoint.class, TradingCardRestEndpoint.ADDRESS);
	}

	@Bean
	public TradingCardBaseRestMapper tradingCardBaseRestMapper() {
		return Mappers.getMapper(TradingCardBaseRestMapper.class);
	}

	@Bean
	public TradingCardFullRestMapper tradingCardFullRestMapper() {
		return Mappers.getMapper(TradingCardFullRestMapper.class);
	}

}

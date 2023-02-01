package com.cezarykluczynski.stapi.server.trading_card_set.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetRestEndpoint;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TradingCardSetConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server tradingCardSetServer() {
		return endpointFactory.createRestEndpoint(TradingCardSetRestEndpoint.class, TradingCardSetRestEndpoint.ADDRESS);
	}

	@Bean
	public TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper() {
		return Mappers.getMapper(TradingCardSetBaseRestMapper.class);
	}

	@Bean
	public TradingCardSetFullRestMapper tradingCardSetFullRestMapper() {
		return Mappers.getMapper(TradingCardSetFullRestMapper.class);
	}

}

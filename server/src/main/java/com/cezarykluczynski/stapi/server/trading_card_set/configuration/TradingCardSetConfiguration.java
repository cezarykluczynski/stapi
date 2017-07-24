package com.cezarykluczynski.stapi.server.trading_card_set.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetRestEndpoint;
import com.cezarykluczynski.stapi.server.trading_card_set.endpoint.TradingCardSetSoapEndpoint;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class TradingCardSetConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint tradingCardSetEndpoint() {
		return endpointFactory.createSoapEndpoint(TradingCardSetSoapEndpoint.class, TradingCardSetSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server tradingCardSetServer() {
		return endpointFactory.createRestEndpoint(TradingCardSetRestEndpoint.class, TradingCardSetRestEndpoint.ADDRESS);
	}

	@Bean
	public TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper() {
		return Mappers.getMapper(TradingCardSetBaseSoapMapper.class);
	}

	@Bean
	public TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper() {
		return Mappers.getMapper(TradingCardSetFullSoapMapper.class);
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

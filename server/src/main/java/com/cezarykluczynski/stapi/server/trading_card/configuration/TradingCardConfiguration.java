package com.cezarykluczynski.stapi.server.trading_card.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.trading_card.endpoint.TradingCardRestEndpoint;
import com.cezarykluczynski.stapi.server.trading_card.endpoint.TradingCardSoapEndpoint;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class TradingCardConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint tradingCardEndpoint() {
		return endpointFactory.createSoapEndpoint(TradingCardSoapEndpoint.class, TradingCardSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server tradingCardServer() {
		return endpointFactory.createRestEndpoint(TradingCardRestEndpoint.class, TradingCardRestEndpoint.ADDRESS);
	}

	@Bean
	public TradingCardBaseSoapMapper tradingCardBaseSoapMapper() {
		return Mappers.getMapper(TradingCardBaseSoapMapper.class);
	}

	@Bean
	public TradingCardFullSoapMapper tradingCardFullSoapMapper() {
		return Mappers.getMapper(TradingCardFullSoapMapper.class);
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

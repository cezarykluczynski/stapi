package com.cezarykluczynski.stapi.server.trading_card_deck.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.trading_card_deck.endpoint.TradingCardDeckRestEndpoint;
import com.cezarykluczynski.stapi.server.trading_card_deck.endpoint.TradingCardDeckSoapEndpoint;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class TradingCardDeckConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint tradingCardDeckEndpoint() {
		return endpointFactory.createSoapEndpoint(TradingCardDeckSoapEndpoint.class, TradingCardDeckSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server tradingCardDeckServer() {
		return endpointFactory.createRestEndpoint(TradingCardDeckRestEndpoint.class, TradingCardDeckRestEndpoint.ADDRESS);
	}

	@Bean
	public TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper() {
		return Mappers.getMapper(TradingCardDeckBaseSoapMapper.class);
	}

	@Bean
	public TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper() {
		return Mappers.getMapper(TradingCardDeckFullSoapMapper.class);
	}

	@Bean
	public TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper() {
		return Mappers.getMapper(TradingCardDeckBaseRestMapper.class);
	}

	@Bean
	public TradingCardDeckFullRestMapper tradingCardDeckFullRestMapper() {
		return Mappers.getMapper(TradingCardDeckFullRestMapper.class);
	}

}

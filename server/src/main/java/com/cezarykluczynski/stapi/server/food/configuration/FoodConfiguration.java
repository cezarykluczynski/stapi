package com.cezarykluczynski.stapi.server.food.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.food.endpoint.FoodSoapEndpoint;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class FoodConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint foodEndpoint() {
		return endpointFactory.createSoapEndpoint(FoodSoapEndpoint.class, FoodSoapEndpoint.ADDRESS);
	}

	@Bean
	public FoodBaseSoapMapper foodBaseSoapMapper() {
		return Mappers.getMapper(FoodBaseSoapMapper.class);
	}

	@Bean
	public FoodFullSoapMapper foodFullSoapMapper() {
		return Mappers.getMapper(FoodFullSoapMapper.class);
	}

	@Bean
	public FoodBaseRestMapper foodBaseRestMapper() {
		return Mappers.getMapper(FoodBaseRestMapper.class);
	}

	@Bean
	public FoodFullRestMapper foodFullRestMapper() {
		return Mappers.getMapper(FoodFullRestMapper.class);
	}

}

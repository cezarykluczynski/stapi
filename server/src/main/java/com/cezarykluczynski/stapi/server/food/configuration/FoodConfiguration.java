package com.cezarykluczynski.stapi.server.food.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server foodServer() {
		return endpointFactory.createRestEndpoint(FoodRestEndpoint.class, FoodRestEndpoint.ADDRESS);
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

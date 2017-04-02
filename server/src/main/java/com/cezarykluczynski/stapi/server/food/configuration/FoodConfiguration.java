package com.cezarykluczynski.stapi.server.food.configuration;

import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint;
import com.cezarykluczynski.stapi.server.food.endpoint.FoodSoapEndpoint;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper;
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper;
import com.cezarykluczynski.stapi.server.food.reader.FoodRestReader;
import com.cezarykluczynski.stapi.server.food.reader.FoodSoapReader;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class FoodConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint foodSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new FoodSoapEndpoint(applicationContext.getBean(FoodSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/food");
		return endpoint;
	}

	@Bean
	public FoodRestEndpoint foodRestEndpoint() {
		return new FoodRestEndpoint(applicationContext.getBean(FoodRestReader.class));
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

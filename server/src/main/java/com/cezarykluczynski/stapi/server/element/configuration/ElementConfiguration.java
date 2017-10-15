package com.cezarykluczynski.stapi.server.element.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.element.endpoint.ElementRestEndpoint;
import com.cezarykluczynski.stapi.server.element.endpoint.ElementSoapEndpoint;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseSoapMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class ElementConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint elementEndpoint() {
		return endpointFactory.createSoapEndpoint(ElementSoapEndpoint.class, ElementSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server elementServer() {
		return endpointFactory.createRestEndpoint(ElementRestEndpoint.class, ElementRestEndpoint.ADDRESS);
	}

	@Bean
	public ElementBaseSoapMapper elementBaseSoapMapper() {
		return Mappers.getMapper(ElementBaseSoapMapper.class);
	}

	@Bean
	public ElementFullSoapMapper elementFullSoapMapper() {
		return Mappers.getMapper(ElementFullSoapMapper.class);
	}

	@Bean
	public ElementBaseRestMapper elementBaseRestMapper() {
		return Mappers.getMapper(ElementBaseRestMapper.class);
	}

	@Bean
	public ElementFullRestMapper elementFullRestMapper() {
		return Mappers.getMapper(ElementFullRestMapper.class);
	}

}

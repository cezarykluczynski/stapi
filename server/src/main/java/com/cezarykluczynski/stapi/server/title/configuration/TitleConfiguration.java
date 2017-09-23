package com.cezarykluczynski.stapi.server.title.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.title.endpoint.TitleRestEndpoint;
import com.cezarykluczynski.stapi.server.title.endpoint.TitleSoapEndpoint;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class TitleConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint titleEndpoint() {
		return endpointFactory.createSoapEndpoint(TitleSoapEndpoint.class, TitleSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server titleServer() {
		return endpointFactory.createRestEndpoint(TitleRestEndpoint.class, TitleRestEndpoint.ADDRESS);
	}

	@Bean
	public TitleBaseSoapMapper titleBaseSoapMapper() {
		return Mappers.getMapper(TitleBaseSoapMapper.class);
	}

	@Bean
	public TitleFullSoapMapper titleFullSoapMapper() {
		return Mappers.getMapper(TitleFullSoapMapper.class);
	}

	@Bean
	public TitleBaseRestMapper titleBaseRestMapper() {
		return Mappers.getMapper(TitleBaseRestMapper.class);
	}

	@Bean
	public TitleFullRestMapper titleFullRestMapper() {
		return Mappers.getMapper(TitleFullRestMapper.class);
	}

}

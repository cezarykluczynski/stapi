package com.cezarykluczynski.stapi.server.literature.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureRestEndpoint;
import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureSoapEndpoint;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class LiteratureConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint literatureEndpoint() {
		return endpointFactory.createSoapEndpoint(LiteratureSoapEndpoint.class, LiteratureSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server literatureServer() {
		return endpointFactory.createRestEndpoint(LiteratureRestEndpoint.class, LiteratureRestEndpoint.ADDRESS);
	}

	@Bean
	public LiteratureBaseSoapMapper literatureBaseSoapMapper() {
		return Mappers.getMapper(LiteratureBaseSoapMapper.class);
	}

	@Bean
	public LiteratureFullSoapMapper literatureFullSoapMapper() {
		return Mappers.getMapper(LiteratureFullSoapMapper.class);
	}

	@Bean
	public LiteratureBaseRestMapper literatureBaseRestMapper() {
		return Mappers.getMapper(LiteratureBaseRestMapper.class);
	}

	@Bean
	public LiteratureFullRestMapper literatureFullRestMapper() {
		return Mappers.getMapper(LiteratureFullRestMapper.class);
	}

}

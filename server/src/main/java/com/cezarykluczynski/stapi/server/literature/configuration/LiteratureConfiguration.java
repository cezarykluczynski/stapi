package com.cezarykluczynski.stapi.server.literature.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureRestEndpoint;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiteratureConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server literatureServer() {
		return endpointFactory.createRestEndpoint(LiteratureRestEndpoint.class, LiteratureRestEndpoint.ADDRESS);
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

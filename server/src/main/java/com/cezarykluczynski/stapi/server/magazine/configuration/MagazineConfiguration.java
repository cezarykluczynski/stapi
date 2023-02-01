package com.cezarykluczynski.stapi.server.magazine.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineRestEndpoint;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MagazineConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server magazineServer() {
		return endpointFactory.createRestEndpoint(MagazineRestEndpoint.class, MagazineRestEndpoint.ADDRESS);
	}

	@Bean
	public MagazineBaseRestMapper magazineBaseRestMapper() {
		return Mappers.getMapper(MagazineBaseRestMapper.class);
	}

	@Bean
	public MagazineFullRestMapper magazineFullRestMapper() {
		return Mappers.getMapper(MagazineFullRestMapper.class);
	}

}

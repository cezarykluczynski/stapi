package com.cezarykluczynski.stapi.server.magazine.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineRestEndpoint;
import com.cezarykluczynski.stapi.server.magazine.endpoint.MagazineSoapEndpoint;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MagazineConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint magazineEndpoint() {
		return endpointFactory.createSoapEndpoint(MagazineSoapEndpoint.class, MagazineSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server magazineServer() {
		return endpointFactory.createRestEndpoint(MagazineRestEndpoint.class, MagazineRestEndpoint.ADDRESS);
	}

	@Bean
	public MagazineBaseSoapMapper magazineBaseSoapMapper() {
		return Mappers.getMapper(MagazineBaseSoapMapper.class);
	}

	@Bean
	public MagazineFullSoapMapper magazineFullSoapMapper() {
		return Mappers.getMapper(MagazineFullSoapMapper.class);
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

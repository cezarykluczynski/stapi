package com.cezarykluczynski.stapi.server.comicStrip.configuration;

import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripRestEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class ComicStripConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint comicStripEndpoint() {
		return endpointFactory.createSoapEndpoint(ComicStripSoapEndpoint.class, ComicStripSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server comicStripServer() {
		return endpointFactory.createRestEndpoint(ComicStripRestEndpoint.class, ComicStripRestEndpoint.ADDRESS);
	}

	@Bean
	public ComicStripBaseSoapMapper comicStripBaseSoapMapper() {
		return Mappers.getMapper(ComicStripBaseSoapMapper.class);
	}

	@Bean
	public ComicStripFullSoapMapper comicStripFullSoapMapper() {
		return Mappers.getMapper(ComicStripFullSoapMapper.class);
	}

	@Bean
	public ComicStripBaseRestMapper comicStripBaseRestMapper() {
		return Mappers.getMapper(ComicStripBaseRestMapper.class);
	}

	@Bean
	public ComicStripFullRestMapper comicStripFullRestMapper() {
		return Mappers.getMapper(ComicStripFullRestMapper.class);
	}

}

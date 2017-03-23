package com.cezarykluczynski.stapi.server.comicStrip.configuration;

import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripRestEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripRestReader;
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripSoapReader;
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
public class ComicStripConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint comicStripSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new ComicStripSoapEndpoint(applicationContext.getBean(ComicStripSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/comicStrip");
		return endpoint;
	}

	@Bean
	public ComicStripRestEndpoint comicStripRestEndpoint() {
		return new ComicStripRestEndpoint(applicationContext.getBean(ComicStripRestReader.class));
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

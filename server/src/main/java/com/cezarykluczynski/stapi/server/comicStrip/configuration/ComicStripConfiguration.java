package com.cezarykluczynski.stapi.server.comicStrip.configuration;

import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper;
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
	public ComicStripSoapMapper comicStripSoapMapper() {
		return Mappers.getMapper(ComicStripSoapMapper.class);
	}

	@Bean
	public ComicStripRestMapper comicStripRestMapper() {
		return Mappers.getMapper(ComicStripRestMapper.class);
	}

}

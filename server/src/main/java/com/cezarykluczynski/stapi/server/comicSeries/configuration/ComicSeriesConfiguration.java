package com.cezarykluczynski.stapi.server.comicSeries.configuration;

import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesSoapReader;
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
public class ComicSeriesConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint comicSeriesSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new ComicSeriesSoapEndpoint(applicationContext.getBean(ComicSeriesSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/comicSeries");
		return endpoint;
	}

	@Bean
	public ComicSeriesSoapMapper comicSeriesSoapMapper() {
		return Mappers.getMapper(ComicSeriesSoapMapper.class);
	}

	@Bean
	public ComicSeriesRestMapper comicSeriesRestMapper() {
		return Mappers.getMapper(ComicSeriesRestMapper.class);
	}

}

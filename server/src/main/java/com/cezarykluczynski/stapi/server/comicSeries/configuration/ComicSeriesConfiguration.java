package com.cezarykluczynski.stapi.server.comicSeries.configuration;

import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesRestReader;
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
	public ComicSeriesRestEndpoint comicSeriesRestEndpoint() {
		return new ComicSeriesRestEndpoint(applicationContext.getBean(ComicSeriesRestReader.class));
	}

	@Bean
	public ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper() {
		return Mappers.getMapper(ComicSeriesBaseSoapMapper.class);
	}

	@Bean
	public ComicSeriesFullSoapMapper comicSeriesFullSoapMapper() {
		return Mappers.getMapper(ComicSeriesFullSoapMapper.class);
	}

	@Bean
	public ComicSeriesBaseRestMapper comicSeriesBaseRestMapper() {
		return Mappers.getMapper(ComicSeriesBaseRestMapper.class);
	}

	@Bean
	public ComicSeriesFullRestMapper comicSeriesFullRestMapper() {
		return Mappers.getMapper(ComicSeriesFullRestMapper.class);
	}

}

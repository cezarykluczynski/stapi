package com.cezarykluczynski.stapi.server.series.configuration;

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader;
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader;
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
public class SeriesConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint seriesSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new SeriesSoapEndpoint(applicationContext.getBean(SeriesSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/series");
		return endpoint;
	}

	@Bean
	public SeriesRestEndpoint seriesRestEndpoint() {
		return new SeriesRestEndpoint(applicationContext.getBean(SeriesRestReader.class));
	}

	@Bean
	public SeriesBaseSoapMapper seriesBaseSoapMapper() {
		return Mappers.getMapper(SeriesBaseSoapMapper.class);
	}

	@Bean
	public SeriesFullSoapMapper seriesFullSoapMapper() {
		return Mappers.getMapper(SeriesFullSoapMapper.class);
	}

	@Bean
	public SeriesBaseRestMapper seriesBaseRestMapper() {
		return Mappers.getMapper(SeriesBaseRestMapper.class);
	}

	@Bean
	public SeriesFullRestMapper seriesFullRestMapper() {
		return Mappers.getMapper(SeriesFullRestMapper.class);
	}

}

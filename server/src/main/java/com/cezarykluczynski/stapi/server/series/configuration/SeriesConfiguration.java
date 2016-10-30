package com.cezarykluczynski.stapi.server.series.configuration;

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
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
	public SeriesSoapMapper seriesSoapMapper() {
		return Mappers.getMapper(SeriesSoapMapper.class);
	}

	@Bean
	public SeriesRestMapper seriesRestMapper() {
		return Mappers.getMapper(SeriesRestMapper.class);
	}

}

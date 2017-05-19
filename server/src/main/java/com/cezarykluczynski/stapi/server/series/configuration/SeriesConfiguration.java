package com.cezarykluczynski.stapi.server.series.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class SeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint seriesEndpoint() {
		return endpointFactory.createSoapEndpoint(SeriesSoapEndpoint.class, SeriesSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server seriesServer() {
		return endpointFactory.createRestEndpoint(SeriesRestEndpoint.class, SeriesRestEndpoint.ADDRESS);
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

package com.cezarykluczynski.stapi.server.series.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server seriesServer() {
		return endpointFactory.createRestEndpoint(SeriesRestEndpoint.class, SeriesRestEndpoint.ADDRESS);
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

package com.cezarykluczynski.stapi.server.magazine_series.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.magazine_series.endpoint.MagazineSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MagazineSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server magazineSeriesServer() {
		return endpointFactory.createRestEndpoint(MagazineSeriesRestEndpoint.class, MagazineSeriesRestEndpoint.ADDRESS);
	}

	@Bean
	public MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper() {
		return Mappers.getMapper(MagazineSeriesBaseRestMapper.class);
	}

	@Bean
	public MagazineSeriesFullRestMapper magazineSeriesFullRestMapper() {
		return Mappers.getMapper(MagazineSeriesFullRestMapper.class);
	}

}

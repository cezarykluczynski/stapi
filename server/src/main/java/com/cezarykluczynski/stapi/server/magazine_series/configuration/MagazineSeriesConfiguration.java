package com.cezarykluczynski.stapi.server.magazine_series.configuration;

import com.cezarykluczynski.stapi.server.magazine_series.endpoint.MagazineSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.magazine_series.endpoint.MagazineSeriesSoapEndpoint;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MagazineSeriesConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint magazineSeriesEndpoint() {
		return endpointFactory.createSoapEndpoint(MagazineSeriesSoapEndpoint.class, MagazineSeriesSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server magazineSeriesServer() {
		return endpointFactory.createRestEndpoint(MagazineSeriesRestEndpoint.class, MagazineSeriesRestEndpoint.ADDRESS);
	}

	@Bean
	public MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper() {
		return Mappers.getMapper(MagazineSeriesBaseSoapMapper.class);
	}

	@Bean
	public MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper() {
		return Mappers.getMapper(MagazineSeriesFullSoapMapper.class);
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

package com.cezarykluczynski.stapi.server.staff.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffV2RestEndpoint;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper;
import jakarta.inject.Inject;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaffConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint staffEndpoint() {
		return endpointFactory.createSoapEndpoint(StaffSoapEndpoint.class, StaffSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server staffServer() {
		return endpointFactory.createRestEndpoint(StaffRestEndpoint.class, StaffRestEndpoint.ADDRESS);
	}

	@Bean
	public Server staffV2Server() {
		return endpointFactory.createRestEndpoint(StaffV2RestEndpoint.class, StaffV2RestEndpoint.ADDRESS);
	}

	@Bean
	public StaffBaseSoapMapper staffBaseSoapMapper() {
		return Mappers.getMapper(StaffBaseSoapMapper.class);
	}

	@Bean
	public StaffFullSoapMapper staffFullSoapMapper() {
		return Mappers.getMapper(StaffFullSoapMapper.class);
	}

	@Bean
	public StaffBaseRestMapper staffBaseRestMapper() {
		return Mappers.getMapper(StaffBaseRestMapper.class);
	}

	@Bean
	public StaffFullRestMapper staffFullRestMapper() {
		return Mappers.getMapper(StaffFullRestMapper.class);
	}

}

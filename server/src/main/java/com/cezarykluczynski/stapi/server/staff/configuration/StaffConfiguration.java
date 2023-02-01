package com.cezarykluczynski.stapi.server.staff.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffV2RestEndpoint;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaffConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server staffServer() {
		return endpointFactory.createRestEndpoint(StaffRestEndpoint.class, StaffRestEndpoint.ADDRESS);
	}

	@Bean
	public Server staffV2Server() {
		return endpointFactory.createRestEndpoint(StaffV2RestEndpoint.class, StaffV2RestEndpoint.ADDRESS);
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

package com.cezarykluczynski.stapi.server.medical_condition.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionRestEndpoint;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper;
import jakarta.inject.Inject;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MedicalConditionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Server medicalConditionServer() {
		return endpointFactory.createRestEndpoint(MedicalConditionRestEndpoint.class, MedicalConditionRestEndpoint.ADDRESS);
	}

	@Bean
	public MedicalConditionBaseRestMapper medicalConditionBaseRestMapper() {
		return Mappers.getMapper(MedicalConditionBaseRestMapper.class);
	}

	@Bean
	public MedicalConditionFullRestMapper medicalConditionFullRestMapper() {
		return Mappers.getMapper(MedicalConditionFullRestMapper.class);
	}

}

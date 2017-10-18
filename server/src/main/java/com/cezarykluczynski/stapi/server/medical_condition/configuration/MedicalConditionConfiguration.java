package com.cezarykluczynski.stapi.server.medical_condition.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionRestEndpoint;
import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionSoapEndpoint;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper;
import org.apache.cxf.endpoint.Server;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class MedicalConditionConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint medicalConditionEndpoint() {
		return endpointFactory.createSoapEndpoint(MedicalConditionSoapEndpoint.class, MedicalConditionSoapEndpoint.ADDRESS);
	}

	@Bean
	public Server medicalConditionServer() {
		return endpointFactory.createRestEndpoint(MedicalConditionRestEndpoint.class, MedicalConditionRestEndpoint.ADDRESS);
	}

	@Bean
	public MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper() {
		return Mappers.getMapper(MedicalConditionBaseSoapMapper.class);
	}

	@Bean
	public MedicalConditionFullSoapMapper medicalConditionFullSoapMapper() {
		return Mappers.getMapper(MedicalConditionFullSoapMapper.class);
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

package com.cezarykluczynski.stapi.server.species.configuration;

import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesSoapEndpoint;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesSoapReader;
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
public class SpeciesConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint speciesSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new SpeciesSoapEndpoint(applicationContext.getBean(SpeciesSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/species");
		return endpoint;
	}

	@Bean
	public SpeciesRestEndpoint speciesRestEndpoint() {
		return new SpeciesRestEndpoint(applicationContext.getBean(SpeciesRestReader.class));
	}

	@Bean
	public SpeciesBaseSoapMapper speciesBaseSoapMapper() {
		return Mappers.getMapper(SpeciesBaseSoapMapper.class);
	}

	@Bean
	public SpeciesFullSoapMapper speciesFullSoapMapper() {
		return Mappers.getMapper(SpeciesFullSoapMapper.class);
	}

	@Bean
	public SpeciesBaseRestMapper speciesBaseRestMapper() {
		return Mappers.getMapper(SpeciesBaseRestMapper.class);
	}

	@Bean
	public SpeciesFullRestMapper speciesFullRestMapper() {
		return Mappers.getMapper(SpeciesFullRestMapper.class);
	}

}

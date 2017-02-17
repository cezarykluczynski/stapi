package com.cezarykluczynski.stapi.server.reference.configuration;

import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceConfiguration {

	@Bean
	public ReferenceSoapMapper referenceSoapMapper() {
		return Mappers.getMapper(ReferenceSoapMapper.class);
	}

	@Bean
	public ReferenceRestMapper referenceRestMapper() {
		return Mappers.getMapper(ReferenceRestMapper.class);
	}

}

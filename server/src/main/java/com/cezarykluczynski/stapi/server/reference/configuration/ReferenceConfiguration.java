package com.cezarykluczynski.stapi.server.reference.configuration;

import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceConfiguration {

	@Bean
	public ReferenceRestMapper referenceRestMapper() {
		return Mappers.getMapper(ReferenceRestMapper.class);
	}

}

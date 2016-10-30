package com.cezarykluczynski.stapi.server.common.configuration;

import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class CommonConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public PageMapper pageMapper() {
		return Mappers.getMapper(PageMapper.class);
	}

}

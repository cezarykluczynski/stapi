package com.cezarykluczynski.stapi.etl.astronomical_object.link.configuration;

import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.inject.Inject;

@Configuration
public class AstronomicalObjectLinkConfiguration {

	@Inject
	private AstronomicalObjectRepository astronomicalObjectRepository;

	@Inject
	private StepsProperties stepsProperties;

	@Bean
	public AstronomicalObjectLinkReader astronomicalObjectLinkReader() throws Exception {
		AstronomicalObjectLinkReader astronomicalObjectLinkReader = new AstronomicalObjectLinkReader();
		astronomicalObjectLinkReader.setPageSize(stepsProperties.getLinkAstronomicalObjects().getCommitInterval());
		astronomicalObjectLinkReader.setRepository(astronomicalObjectRepository);
		astronomicalObjectLinkReader.setSort(ImmutableMap.of("id", Sort.Direction.ASC));
		astronomicalObjectLinkReader.setMethodName("findAll");
		astronomicalObjectLinkReader.afterPropertiesSet();
		return astronomicalObjectLinkReader;
	}

}

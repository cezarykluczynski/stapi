package com.cezarykluczynski.stapi.etl.location.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class LocationCreationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public LocationReader locationReader() {
		List<PageHeader> locations = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS)) {
			locations.addAll(categoryApi.getPages(CategoryTitle.LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.LOCATIONS_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_LANDMARKS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_ROADS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.WARDS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.STARFLEET_SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.DS9_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.GEOGRAPHY, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.FICTIONAL_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.BODIES_OF_WATER, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_BODIES_OF_WATER, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.COUNTRIES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_COUNTRIES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SUBNATIONAL_ENTITIES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.BAJORAN_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.COLONIES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SETTLEMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.US_SETTLEMENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.US_SETTLEMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.EARTH_GEOGRAPHY, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.LANDFORMS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.RELIGIOUS_LOCATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.STRUCTURES, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.BUILDING_INTERIORS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.LANDMARKS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.ROADS, MediaWikiSource.MEMORY_ALPHA_EN));
			locations.addAll(categoryApi.getPages(CategoryTitle.SHIPYARDS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new LocationReader(Lists.newArrayList(Sets.newHashSet(locations)));
	}

}

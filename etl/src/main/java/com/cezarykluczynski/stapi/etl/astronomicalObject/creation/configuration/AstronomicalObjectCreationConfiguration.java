package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.configuration;


import com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor.AstronomicalObjectReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
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
public class AstronomicalObjectCreationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public AstronomicalObjectReader astronomicalObjectReader() {
		List<PageHeader> astronomicalObjects = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ASTRONOMICAL_OBJECTS)) {
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.ASTEROIDS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.ASTEROID_BELTS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.COMETS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.CONSTELLATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.GALAXIES, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.HOMEWORLDS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.MOONS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.NEBULAE, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.PLANETS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.PLANETOIDS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.QUASARS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.STAR_SYSTEMS, MediaWikiSource.MEMORY_ALPHA_EN));
			astronomicalObjects.addAll(categoryApi.getPages(CategoryName.STARS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new AstronomicalObjectReader(Lists.newArrayList(Sets.newHashSet(astronomicalObjects)));
	}

}

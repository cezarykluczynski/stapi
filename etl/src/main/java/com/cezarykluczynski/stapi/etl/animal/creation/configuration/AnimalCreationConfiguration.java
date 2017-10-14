package com.cezarykluczynski.stapi.etl.animal.creation.configuration;

import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class AnimalCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public AnimalReader animalReader() {
		List<PageHeader> animals = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ANIMALS)) {
			animals.addAll(categoryApi.getPages(CategoryTitle.ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN));
			animals.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.EARTH_ANIMALS, MediaWikiSource.MEMORY_ALPHA_EN));
			animals.addAll(categoryApi.getPages(CategoryTitle.AVIANS, MediaWikiSource.MEMORY_ALPHA_EN));
			animals.addAll(categoryApi.getPages(CategoryTitle.CANINES, MediaWikiSource.MEMORY_ALPHA_EN));
			animals.addAll(categoryApi.getPages(CategoryTitle.FELINES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new AnimalReader(Lists.newArrayList(Sets.newHashSet(animals)));
	}

}

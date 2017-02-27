package com.cezarykluczynski.stapi.etl.species.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class SpeciesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public SpeciesReader speciesReader() {
		List<PageHeader> pageHeaderList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES)) {
			pageHeaderList.addAll(categoryApi.getPages(CategoryTitle.SPECIES, MediaWikiSource.MEMORY_ALPHA_EN));
			pageHeaderList.addAll(categoryApi.getPages(CategoryTitle.UNNAMED_SPECIES, MediaWikiSource.MEMORY_ALPHA_EN));
			pageHeaderList.addAll(categoryApi.getPages(CategoryTitle.NON_CORPOREALS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpeciesReader(pageHeaderList);
	}

}

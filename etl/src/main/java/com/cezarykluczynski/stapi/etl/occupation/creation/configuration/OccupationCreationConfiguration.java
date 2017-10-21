package com.cezarykluczynski.stapi.etl.occupation.creation.configuration;


import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationReader;
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
public class OccupationCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public OccupationReader occupationReader() {
		List<PageHeader> occupations = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_OCCUPATIONS)) {
			occupations.addAll(categoryApi.getPages(CategoryTitle.OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			occupations.addAll(categoryApi.getPages(CategoryTitle.LEGAL_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			occupations.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			occupations.addAll(categoryApi.getPages(CategoryTitle.SCIENTIFIC_OCCUPATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new OccupationReader(Lists.newArrayList(Sets.newHashSet(occupations)));
	}

}

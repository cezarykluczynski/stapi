package com.cezarykluczynski.stapi.etl.technology.creation.configuration;


import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyReader;
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
public class TechnologyCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public TechnologyReader technologyReader() {
		List<PageHeader> technology = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TECHNOLOGY)) {
			technology.addAll(categoryApi.getPages(CategoryTitle.TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.BORG_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.BORG_COMPONENTS, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.COMMUNICATIONS_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.COMMUNICATIONS_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.COMPUTER_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.COMPUTER_PROGRAMMING, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.SUBROUTINES, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.DATABASES, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.ENERGY_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.ENERGY_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.FICTIONAL_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.HOLOGRAPHIC_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.IDENTIFICATION_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.LIFE_SUPPORT_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.SENSOR_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.SENSOR_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.SHIELD_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.SHIELD_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.TOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.CULINARY_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.ENGINEERING_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.HOUSEHOLD_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.MEDICAL_EQUIPMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			technology.addAll(categoryApi.getPages(CategoryTitle.TRANSPORTER_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new TechnologyReader(Lists.newArrayList(Sets.newHashSet(technology)));
	}

}

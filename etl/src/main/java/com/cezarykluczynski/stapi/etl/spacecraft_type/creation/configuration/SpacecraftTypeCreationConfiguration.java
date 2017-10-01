package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.configuration;


import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader;
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
public class SpacecraftTypeCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public SpacecraftTypeReader spacecraftTypeReader() {
		List<PageHeader> spacecraftTypes = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_TYPES)) {
			spacecraftTypes.addAll(categoryApi.getPages(CategoryTitle.SPACECRAFT_CLASSIFICATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftTypes.addAll(categoryApi.getPages(CategoryTitle.STATION_TYPES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpacecraftTypeReader(Lists.newArrayList(Sets.newHashSet(spacecraftTypes)));
	}

}

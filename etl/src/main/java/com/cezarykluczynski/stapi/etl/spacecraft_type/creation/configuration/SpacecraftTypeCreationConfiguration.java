package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class SpacecraftTypeCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public SpacecraftTypeReader spacecraftTypeReader() {
		List<PageHeader> spacecraftTypes = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_TYPES)) {
			spacecraftTypes.addAll(categoryApi.getPages(CategoryTitles.SPACECRAFT_TYPES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpacecraftTypeReader(SortingUtil.sortedUnique(spacecraftTypes));
	}

}

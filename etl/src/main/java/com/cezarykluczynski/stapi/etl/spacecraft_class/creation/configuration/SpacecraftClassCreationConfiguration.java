package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassReader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
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
public class SpacecraftClassCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public SpacecraftClassReader spacecraftClassReader() {
		List<PageHeader> spacecraftClasses = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_CLASSES)) {
			spacecraftClasses.addAll(categoryApi.getPages(CategoryTitles.SPACECRAFT_CLASSES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasses.addAll(categoryApi.getPagesIncludingSubcategoriesExcept(CategoryTitle.STARSHIP_CLASSES,
					Lists.newArrayList(CategoryTitle.MEMORY_ALPHA_NON_CANON_REDIRECTS_STARSHIP_CLASSES), MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpacecraftClassReader(SortingUtil.sortedUnique(spacecraftClasses));
	}

}

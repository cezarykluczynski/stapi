package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.configuration;


import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassReader;
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
public class SpacecraftClassCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public SpacecraftClassReader spacecraftClassReader() {
		List<PageHeader> spacecraftClasss = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFT_CLASSES)) {
			spacecraftClasss.addAll(categoryApi.getPages(CategoryTitle.SPACECRAFT_CLASSES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasss.addAll(categoryApi.getPages(CategoryTitle.ESCAPE_POD_CLASSES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasss.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SPACECRAFT_CLASSES_ALTERNATE_REALITY,
					MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasss.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.EARTH_SPACECRAFT_CLASSES,
					MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasss.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SHUTTLE_CLASSES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecraftClasss.addAll(categoryApi.getPagesIncludingSubcategoriesExcept(CategoryTitle.STARSHIP_CLASSES,
					Lists.newArrayList(CategoryTitle.MEMORY_ALPHA_NON_CANON_REDIRECTS_STARSHIP_CLASSES), MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpacecraftClassReader(Lists.newArrayList(Sets.newHashSet(spacecraftClasss)));
	}

}

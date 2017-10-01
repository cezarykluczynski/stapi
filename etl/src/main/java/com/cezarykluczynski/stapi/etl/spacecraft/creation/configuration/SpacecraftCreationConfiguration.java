package com.cezarykluczynski.stapi.etl.spacecraft.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftReader;
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
public class SpacecraftCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public SpacecraftReader spacecraftReader() {
		List<PageHeader> spacecrafts = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFTS)) {
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.SPACECRAFT_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.SHUTTLES_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.STARSHIPS_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.FEDERATION_STARSHIPS_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.ESCAPE_PODS, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPages(CategoryTitle.PROBES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.EARTH_SPACECRAFT, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SHUTTLES, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.SPACE_STATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			spacecrafts.addAll(categoryApi.getPagesIncludingSubcategoriesExcept(CategoryTitle.STARSHIPS, Lists
					.newArrayList(CategoryTitle.MEMORY_ALPHA_NON_CANON_REDIRECTS_STARSHIPS), MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SpacecraftReader(Lists.newArrayList(Sets.newHashSet(spacecrafts)));
	}

}

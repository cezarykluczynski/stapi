package com.cezarykluczynski.stapi.etl.material.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialReader;
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
public class MaterialCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public MaterialReader materialReader() {
		List<PageHeader> materials = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MATERIALS)) {
			materials.addAll(categoryApi.getPages(CategoryTitle.MATERIALS, MediaWikiSource.MEMORY_ALPHA_EN));
			materials.addAll(categoryApi.getPages(CategoryTitle.EXPLOSIVES, MediaWikiSource.MEMORY_ALPHA_EN));
			materials.addAll(categoryApi.getPages(CategoryTitle.GEMSTONES, MediaWikiSource.MEMORY_ALPHA_EN));
			materials.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.CHEMICAL_COMPOUNDS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new MaterialReader(Lists.newArrayList(Sets.newHashSet(materials)));
	}

}

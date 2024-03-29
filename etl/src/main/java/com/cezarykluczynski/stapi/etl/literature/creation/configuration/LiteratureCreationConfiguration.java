package com.cezarykluczynski.stapi.etl.literature.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureReader;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Configuration
public class LiteratureCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public LiteratureReader literatureReader() {
		List<PageHeader> literatureList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LITERATURE)) {
			literatureList.addAll(categoryApi.getPages(CategoryTitle.LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.EARTH_LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.SHAKESPEAREAN_WORKS, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.REPORTS, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.SCIENTIFIC_LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.SCIENTIFIC_LITERATURE_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.TECHNICAL_MANUALS, MediaWikiSource.MEMORY_ALPHA_EN));
			literatureList.addAll(categoryApi.getPages(CategoryTitle.VULCAN_LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new LiteratureReader(SortingUtil.sortedUnique(literatureList));
	}

}

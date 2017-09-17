package com.cezarykluczynski.stapi.etl.title.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleReader;
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
public class TitleCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public TitleReader titleReader() {
		List<PageHeader> titles = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TITLES)) {
			titles.addAll(categoryApi.getPages(CategoryTitle.TITLES, MediaWikiSource.MEMORY_ALPHA_EN));
			titles.addAll(categoryApi.getPages(CategoryTitle.MILITARY_RANKS, MediaWikiSource.MEMORY_ALPHA_EN));
			titles.addAll(categoryApi.getPages(CategoryTitle.RELIGIOUS_TITLES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new TitleReader(Lists.newArrayList(Sets.newHashSet(titles)));
	}

}

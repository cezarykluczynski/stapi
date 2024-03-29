package com.cezarykluczynski.stapi.etl.episode.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
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
public class EpisodeCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Inject
	private ModuleEpisodeDataProvider moduleEpisodeDataProvider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public EpisodeReader episodeReader() {
		List<PageHeader> episodes = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_EPISODES)) {
			episodes.addAll(categoryApi.getPages(CategoryTitles.EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			moduleEpisodeDataProvider.initialize();
		}

		return new EpisodeReader(SortingUtil.sortedUnique(episodes));
	}

}

package com.cezarykluczynski.stapi.etl.episode.creation.configuration;

import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class EpisodeCreationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private JobCompletenessDecider jobCompletenessDecider;

	// ensure Spring Batch migrates it's schema before reader is instantiated
	@Inject
	private BatchDatabaseInitializer batchDatabaseInitializer;

	@Bean
	public EpisodeReader episodeReader() {
		List<PageHeader> episodes = Lists.newArrayList();

		if (!jobCompletenessDecider.isStepComplete(JobCompletenessDecider.STEP_005_CREATE_EPISODES)) {
			episodes.addAll(categoryApi.getPages(CategoryName.TOS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.TAS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.TNG_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.DS9_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.VOY_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.ENT_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
			episodes.addAll(categoryApi.getPages(CategoryName.DIS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new EpisodeReader(episodes);
	}

}

package com.cezarykluczynski.stapi.etl.movie.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader;
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
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
public class MovieCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Inject
	private ModuleEpisodeDataProvider moduleEpisodeDataProvider;

	@Inject
	private MovieExistingEntitiesHelper movieExistingEntitiesHelper;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public MovieReader movieReader() {
		List<PageHeader> movies = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MOVIES)) {
			movies.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_FILMS, MediaWikiSource.MEMORY_ALPHA_EN));
			moduleEpisodeDataProvider.initialize();
			movieExistingEntitiesHelper.initialize();
		}

		return new MovieReader(SortingUtil.sortedUnique(movies));
	}

}

package com.cezarykluczynski.stapi.etl.comic_series.creation.configuration;


import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesReader;
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
public class ComicSeriesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public ComicSeriesReader comicSeriesReader() {
		List<PageHeader> comicSeriess = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_SERIES)) {
			comicSeriess.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.COMIC_SERIES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new ComicSeriesReader(Lists.newArrayList(Sets.newHashSet(comicSeriess)));
	}

}

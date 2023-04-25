package com.cezarykluczynski.stapi.etl.comic_series.creation.configuration;

import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
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
public class ComicSeriesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public ComicSeriesReader comicSeriesReader() {
		List<PageHeader> comicSeriess = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_SERIES)) {
			comicSeriess.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.COMIC_SERIES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new ComicSeriesReader(SortingUtil.sortedUnique(comicSeriess));
	}

}

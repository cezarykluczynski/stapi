package com.cezarykluczynski.stapi.etl.comics.creation.configuration;

import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsReader;
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
public class ComicsCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public ComicsReader comicsReader() {
		List<PageHeader> comicsList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMICS)) {
			comicsList.addAll(categoryApi.getPages(CategoryTitle.COMICS, MediaWikiSource.MEMORY_ALPHA_EN));
			comicsList.addAll(categoryApi.getPages(CategoryTitle.COMIC_ADAPTATIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			comicsList.addAll(categoryApi.getPages(CategoryTitle.PHOTONOVELS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new ComicsReader(SortingUtil.sortedUnique(comicsList));
	}

}

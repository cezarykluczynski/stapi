package com.cezarykluczynski.stapi.etl.comic_collection.creation.configuration;

import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader;
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
public class ComicCollectionCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDataSourceInitializer")
	public ComicCollectionReader comicCollectionReader() {
		List<PageHeader> comicCollectionList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_COLLECTIONS)) {
			comicCollectionList.addAll(categoryApi.getPages(CategoryTitle.COMIC_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			comicCollectionList.addAll(categoryApi.getPages(CategoryTitle.PHOTONOVELS_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new ComicCollectionReader(SortingUtil.sortedUnique(comicCollectionList));
	}

}

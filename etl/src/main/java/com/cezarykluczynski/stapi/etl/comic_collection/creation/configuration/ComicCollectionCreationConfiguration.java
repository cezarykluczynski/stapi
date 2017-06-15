package com.cezarykluczynski.stapi.etl.comic_collection.creation.configuration;

import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader;
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
public class ComicCollectionCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public ComicCollectionReader comicCollectionReader() {
		List<PageHeader> comicCollectionList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_COLLECTIONS)) {
			comicCollectionList.addAll(categoryApi.getPages(CategoryTitle.COMIC_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN));
			comicCollectionList.addAll(categoryApi.getPages(CategoryTitle.PHOTONOVELS_COLLECTIONS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new ComicCollectionReader(Lists.newArrayList(Sets.newHashSet(comicCollectionList)));
	}

}

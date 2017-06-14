package com.cezarykluczynski.stapi.etl.book_series.creation.configuration;


import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
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
public class BookSeriesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public BookSeriesReader bookSeriesReader() {
		List<PageHeader> bookSeriess = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOK_SERIES)) {
			bookSeriess.addAll(categoryApi.getPages(CategoryTitles.BOOK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new BookSeriesReader(Lists.newArrayList(Sets.newHashSet(bookSeriess)));
	}

}

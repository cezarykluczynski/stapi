package com.cezarykluczynski.stapi.etl.book.creation.configuration;

import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader;
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
public class BookCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public BookReader bookReader() {
		List<PageHeader> bookList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_BOOKS)) {
			bookList.addAll(categoryApi.getPages(CategoryTitles.BOOKS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new BookReader(Lists.newArrayList(Sets.newHashSet(bookList)));
	}

}

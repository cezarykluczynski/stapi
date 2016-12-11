package com.cezarykluczynski.stapi.etl.series.creation.configuration;

import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class SeriesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private JobCompletenessDecider jobCompletenessDecider;

	// ensure Spring Batch migrates it's schema before reader is instantiated
	@Inject
	private BatchDatabaseInitializer batchDatabaseInitializer;

	@Bean
	public SeriesReader seriesReader() {
		List<PageHeader> pageHeaderList = Lists.newArrayList();

		if (!jobCompletenessDecider.isStepComplete(StepName.CREATE_SERIES)) {
			pageHeaderList.addAll(categoryApi.getPages(CategoryName.STAR_TREK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new SeriesReader(pageHeaderList);
	}

}

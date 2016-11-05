package com.cezarykluczynski.stapi.etl.series.creation.configuration;

import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class SeriesCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepBuilderFactory stepBuilderFactory;

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public SeriesReader seriesReader() {
		return new SeriesReader(categoryApi.getPages(CategoryName.STAR_TREK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN));
	}

}

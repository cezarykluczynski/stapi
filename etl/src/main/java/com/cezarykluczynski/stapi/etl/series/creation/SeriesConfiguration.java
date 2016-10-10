package com.cezarykluczynski.stapi.etl.series.creation;

import com.cezarykluczynski.stapi.etl.common.CommonStepExecutionListener;
import com.cezarykluczynski.stapi.etl.util.Steps;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.util.constants.Categories;
import com.cezarykluczynski.stapi.wiki.api.CategoryApi;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class SeriesConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepBuilderFactory stepBuilderFactory;

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public SeriesReader seriesReader() {
		return new SeriesReader(categoryApi.getPages(Categories.STAR_TREK_SERIES));
	}

	@Bean(name = Steps.STEP_001_CREATE_SERIES)
	public Step step() {
		return stepBuilderFactory.get(Steps.STEP_001_CREATE_SERIES)
				.<PageHeader, Series> chunk(10)
				.reader(applicationContext.getBean(SeriesReader.class))
				.processor(applicationContext.getBean(SeriesProcessor.class))
				.writer(applicationContext.getBean(SeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.build();
	}

}

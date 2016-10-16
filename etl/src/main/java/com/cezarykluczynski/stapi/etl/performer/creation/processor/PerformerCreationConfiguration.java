package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener;
import com.cezarykluczynski.stapi.etl.util.Steps;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.util.constants.CategoryName;
import com.cezarykluczynski.stapi.wiki.api.CategoryApi;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class PerformerCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepBuilderFactory stepBuilderFactory;

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public PerformerReader performerReader() {
		return new PerformerReader(categoryApi.getPages(CategoryName.PERFORMERS));
	}

	@Bean(name = Steps.STEP_002_CREATE_PERFORMERS)
	public Step step() {
		return stepBuilderFactory.get(Steps.STEP_002_CREATE_PERFORMERS)
				.<PageHeader, Performer> chunk(50)
				.reader(applicationContext.getBean(PerformerReader.class))
				.processor(applicationContext.getBean(PerformerProcessor.class))
				.writer(applicationContext.getBean(PerformerWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}


}

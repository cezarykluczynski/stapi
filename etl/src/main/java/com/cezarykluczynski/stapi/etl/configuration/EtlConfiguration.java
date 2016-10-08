package com.cezarykluczynski.stapi.etl.configuration;

import com.cezarykluczynski.stapi.etl.util.Steps;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableBatchProcessing
public class EtlConfiguration {

	@Inject
	private JobBuilderFactory jobBuilderFactory;

	@Inject
	private ApplicationContext ctx;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.flow(ctx.getBean(Steps.STEP_001_CREATE_SERIES, Step.class))
				.end()
				.build();
	}

}

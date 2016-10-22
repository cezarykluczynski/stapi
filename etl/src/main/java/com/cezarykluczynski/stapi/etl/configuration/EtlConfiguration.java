package com.cezarykluczynski.stapi.etl.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Profile("etl")
@Configuration
@EnableBatchProcessing
@ImportResource(locations = {"classpath:spring/batch/jobs/create.xml"})
@ComponentScan({
		"com.cezarykluczynski.stapi.etl",
		"com.cezarykluczynski.stapi.wiki"
})
public class EtlConfiguration {

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}

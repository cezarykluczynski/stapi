package com.cezarykluczynski.stapi.etl.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.EtlJobConfiguration;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Profile(SpringProfile.ETL)
@Configuration
@EnableBatchProcessing
@Import(EtlJobConfiguration.class)
@ComponentScan({
		"com.cezarykluczynski.stapi.etl",
		"com.cezarykluczynski.stapi.sources"
})
public class EtlConfiguration {

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}

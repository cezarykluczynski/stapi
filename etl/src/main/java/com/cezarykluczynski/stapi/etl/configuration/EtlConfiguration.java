package com.cezarykluczynski.stapi.etl.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.EtlJobConfiguration;
import com.cezarykluczynski.stapi.etl.configuration.job.SpringBatchDaoConfiguration;
import com.cezarykluczynski.stapi.util.constant.Package;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@ConditionalOnProperty(value = "spring.batch.job.enabled", havingValue = "true")
@Configuration
@Import({EtlJobConfiguration.class, SpringBatchDaoConfiguration.class})
@ComponentScan({
		Package.ETL
})
public class EtlConfiguration {

	@Bean
	public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}

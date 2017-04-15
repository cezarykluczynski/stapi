package com.cezarykluczynski.stapi.etl.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.EtlJobConfiguration;
import com.cezarykluczynski.stapi.etl.configuration.job.SpringBatchDaoConfiguration;
import com.cezarykluczynski.stapi.util.constant.Package;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Profile(SpringProfile.ETL)
@Configuration
@EnableBatchProcessing
@Import({EtlJobConfiguration.class, SpringBatchDaoConfiguration.class})
@ComponentScan({
		Package.ETL,
		Package.SOURCES
})
public class EtlConfiguration {

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}

package com.cezarykluczynski.stapi.model.configuration;

import com.cezarykluczynski.stapi.util.constant.Package;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.support.Repositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, HibernateProperties.class})
@EnableJpaRepositories(basePackages = ModelConfiguration.JPA_BASE_PACKAGES)
@EntityScan(basePackages = ModelConfiguration.JPA_BASE_PACKAGES)
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableCaching
public class ModelConfiguration {

	static final String JPA_BASE_PACKAGES = Package.MODEL;

	@Inject
	private ApplicationContext applicationContext;

	@Bean(name = "liquibase")
	@ConditionalOnProperty(name = "spring.liquibase.enabled", havingValue = "false")
	public Object liquibaseMock() {
		return new Object();
	}

	@Bean
	public Repositories repositories() {
		return new Repositories(applicationContext);
	}

}

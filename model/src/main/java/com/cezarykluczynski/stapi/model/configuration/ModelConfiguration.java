package com.cezarykluczynski.stapi.model.configuration;

import com.cezarykluczynski.stapi.model.common.cache.CacheProperties;
import com.cezarykluczynski.stapi.model.common.etl.EtlProperties;
import com.cezarykluczynski.stapi.util.constant.Package;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import net.sf.ehcache.CacheManager;
import org.hibernate.jpa.AvailableSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.support.Repositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, HibernateProperties.class, ThrottleProperties.class, CacheProperties.class,
		EtlProperties.class})
@EnableJpaRepositories(basePackages = ModelConfiguration.JPA_BASE_PACKAGES)
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class ModelConfiguration {

	static final String JPA_BASE_PACKAGES = Package.MODEL;

	private static final String HIBERNATE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";

	private static final String DATASOURCE_PREFIX = "stapi.datasource.main";

	private static final String TRUE = "true";

	@Inject
	private HibernateProperties hibernateProperties;

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private Environment environment;

	@Inject
	private EtlProperties etlProperties;

	@Bean
	@ConfigurationProperties(prefix = DATASOURCE_PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource());
		lef.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lef.setPackagesToScan(ModelConfiguration.JPA_BASE_PACKAGES);
		lef.setPersistenceUnitName(AvailableSettings.PERSISTENCE_UNIT_NAME);
		Map<String, String> properties = Maps.newHashMap();
		properties.put("hibernate.implicit_naming_strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
		properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		if (Boolean.TRUE.equals(etlProperties.getEnabled())) {
			properties.put(HIBERNATE_USE_SECOND_LEVEL_CACHE, "false");
		} else {
			properties.put(HIBERNATE_USE_SECOND_LEVEL_CACHE, TRUE);
			properties.put("hibernate.cache.use_query_cache", TRUE);
			properties.put("hibernate.generate_statistics", TRUE);
			properties.put("hibernate.enable_lazy_load_no_trans", TRUE);
			properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		}
		properties.put("spring.jpa.properties.hibernate.show_sql", TRUE);
		properties.put("spring.jpa.properties.hibernate.format_sql", TRUE);
		properties.put("spring.jpa.hibernate.show_sql", TRUE);
		properties.put("spring.jpa.hibernate.format_sql", TRUE);
		properties.put("hibernate.dialect", hibernateProperties.getDialect());
		properties.put("hibernate.default_schema", hibernateProperties.getDefaultSchema());
		lef.setJpaPropertyMap(properties);
		lef.afterPropertiesSet();
		return lef.getObject();
	}

	@Bean
	@ConditionalOnProperty("liquibase.enabled")
	public SpringLiquibase liquibase() {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setChangeLog("classpath:liquibase/changelog.xml");
		springLiquibase.setDefaultSchema("stapi");
		springLiquibase.setDataSource(dataSource());
		return springLiquibase;
	}

	@Bean(name = "liquibase")
	@ConditionalOnProperty(name = "liquibase.enabled", havingValue = "false")
	public Object liquibaseMock() {
		return new Object();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

	@Bean
	public Repositories repositories() {
		return new Repositories(applicationContext);
	}

	@Bean
	public EhCacheInfoContributor ehCacheInfoContributor() {
		return new EhCacheInfoContributor(CacheManager.getInstance());
	}

}

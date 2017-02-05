package com.cezarykluczynski.stapi.model.configuration;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.AvailableSettings;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, HibernateProperties.class})
@EnableJpaRepositories(basePackages = ModelConfiguration.JPA_BASE_PACKAGES)
@EnableTransactionManagement
public class ModelConfiguration {

	static final String JPA_BASE_PACKAGES = "com.cezarykluczynski.stapi.model";

	private static final String DATASOURCE_PREFIX = "stapi.datasource.main";

	private static final String TRUE = "true";

	@Inject
	private HibernateProperties hibernateProperties;

	@Bean
	@ConfigurationProperties(prefix = DATASOURCE_PREFIX)
	DataSource dataSource() {
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
		properties.put("spring.jpa.properties.hibernate.show_sql", TRUE);
		properties.put("spring.jpa.properties.hibernate.format_sql", TRUE);
		properties.put("spring.jpa.hibernate.show_sql", TRUE);
		properties.put("spring.jpa.hibernate.format_sql", TRUE);
		properties.put("hibernate.dialect", hibernateProperties.getDialect());
		lef.setJpaPropertyMap(properties);
		lef.afterPropertiesSet();
		return lef.getObject();
	}

	@Bean
	@Profile(SpringProfile.ETL)
	public SpringLiquibase liquibase() {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setChangeLog("classpath:liquibase/changelog.xml");
		springLiquibase.setDataSource(dataSource());
		return springLiquibase;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

}

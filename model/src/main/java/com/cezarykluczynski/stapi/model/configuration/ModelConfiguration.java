package com.cezarykluczynski.stapi.model.configuration;

import com.cezarykluczynski.stapi.util.constant.SpringProfiles;
import com.google.common.collect.Maps;
import liquibase.spring.SpringLiquibase;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

import static com.cezarykluczynski.stapi.model.configuration.ModelConfiguration.JPA_BASE_PACKAGES;
import static org.hibernate.jpa.AvailableSettings.PERSISTENCE_UNIT_NAME;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableJpaRepositories(basePackages = JPA_BASE_PACKAGES)
@EnableTransactionManagement
public class ModelConfiguration {

	private static final String DATASOURCE_PREFIX = "stapi.datasource.main";

	public static final String JPA_BASE_PACKAGES = "com.cezarykluczynski.stapi.model";

	@Bean
	@ConfigurationProperties(prefix = DATASOURCE_PREFIX)
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource());
		lef.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lef.setPackagesToScan(JPA_BASE_PACKAGES);
		lef.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		Map<String, String> properties = Maps.newHashMap();
		properties.put("hibernate.implicit_naming_strategy",
				"org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
		properties.put("hibernate.physical_naming_strategy",
				"org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		properties.put("spring.jpa.properties.hibernate.show_sql", "true");
		properties.put("spring.jpa.properties.hibernate.format_sql", "true");
		properties.put("spring.jpa.hibernate.show_sql", "true");
		properties.put("spring.jpa.hibernate.format_sql", "true");
		lef.setJpaPropertyMap(properties);
		lef.afterPropertiesSet();
		return lef.getObject();
	}

	@Bean
	@Profile(SpringProfiles.ETL)
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

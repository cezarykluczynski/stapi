package com.cezarykluczynski.stapi.server;

import com.cezarykluczynski.stapi.etl.configuration.EtlConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@ComponentScan({
		"com.cezarykluczynski.stapi.server",
		"com.cezarykluczynski.stapi.model"
})
@Import(EtlConfiguration.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, LiquibaseAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		produceSpringApplicationBuilder().run(args);
	}

	public static SpringApplicationBuilder produceSpringApplicationBuilder() {
		return new Application().configure(new SpringApplicationBuilder(Application.class));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

}

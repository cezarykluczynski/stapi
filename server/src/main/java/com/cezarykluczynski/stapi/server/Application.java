package com.cezarykluczynski.stapi.server;

import com.cezarykluczynski.stapi.etl.configuration.EtlConfiguration;
import com.cezarykluczynski.stapi.util.constant.Package;
import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@ComponentScan({
		Package.AUTH,
		Package.SERVER,
		Package.SOURCES,
		Package.MODEL,
		Package.CONTRACT,
		Package.UTIL
})
@Import(EtlConfiguration.class)
@EnableAutoConfiguration(exclude = {CxfAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		JmxAutoConfiguration.class, LiquibaseAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application extends SpringBootServletInitializer {

	@SuppressWarnings("UncommentedMain")
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

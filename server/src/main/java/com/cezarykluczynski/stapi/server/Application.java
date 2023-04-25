package com.cezarykluczynski.stapi.server;

import com.cezarykluczynski.stapi.etl.configuration.EtlConfiguration;
import com.cezarykluczynski.stapi.util.constant.Package;
import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication(exclude = {CxfAutoConfiguration.class}, scanBasePackages = Package.ROOT)
@ComponentScan({
		Package.SERVER,
		Package.MODEL,
		Package.UTIL
})
@Import(EtlConfiguration.class)
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

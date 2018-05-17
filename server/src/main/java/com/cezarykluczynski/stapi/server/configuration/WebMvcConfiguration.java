package com.cezarykluczynski.stapi.server.configuration;

import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import org.apache.catalina.Context;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Inject
	private FeatureSwitchApi featureSwitchApi;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/**")
				.addResourceLocations("classpath:/build/");
	}

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void configureContext(Context context, ServletContextInitializer[] initializers) {
				if (!featureSwitchApi.isEnabled(FeatureSwitchType.ADMIN_PANEL)) {
					context.setCookies(false);
				}
				super.configureContext(context, initializers);
			}
		};
	}

}

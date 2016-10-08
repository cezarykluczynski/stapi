package com.cezarykluczynski.stapi.wiki.connector.bliki;

import info.bliki.api.Connector;
import info.bliki.api.User;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({BlikiConnectorProperties.class})
public class BlikiConnectorConfiguration {

	@Inject
	private BlikiConnectorProperties blikiConnectorProperties;

	@Bean
	public User user() {
		User user = new User("", "", blikiConnectorProperties.getSourceUrl());
		user.login();
		return user;
	}

	@Bean
	public Connector connector() {
		return new Connector();
	}

}

package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import info.bliki.api.Connector;
import info.bliki.api.User;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({MediaWikiSourcesProperties.class})
public class BlikiConnectorConfiguration {

	@Inject
	private MediaWikiSourcesProperties mediaWikiSourcesProperties;

	@Bean
	public User user() {
		User user = new User("", "", mediaWikiSourcesProperties.getMemoryAlpha());
		user.login();
		return user;
	}

	@Bean
	public Connector connector() {
		return new Connector();
	}

}

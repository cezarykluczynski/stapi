package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({MediaWikiSourcesProperties.class})
public class BlikiConnectorConfiguration {

	public static final String MEMORY_ALPHA_EN_USER_DECORATOR = "MEMORY_ALPHA_EN_USER_DECORATOR";
	public static final String MEMORY_BETA_EN_USER_DECORATOR = "MEMORY_BETA_EN_USER_DECORATOR";
	public static final String TECHNICAL_HELPER_USER_DECORATOR = "TECHNICAL_HELPER_USER_DECORATOR";

	@Inject
	private MediaWikiSourcesProperties mediaWikiSourcesProperties;

	@Bean(name = MEMORY_ALPHA_EN_USER_DECORATOR)
	public UserDecorator memoryAlphaEnUserDecorator() {
		UserDecorator userDecorator = new UserDecorator("", "", mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl());
		userDecorator.login();
		return userDecorator;
	}

	@Bean(name = MEMORY_BETA_EN_USER_DECORATOR)
	public UserDecorator memoryBetaEnUserDecorator() {
		UserDecorator userDecorator = new UserDecorator("", "", mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl());
		userDecorator.login();
		return userDecorator;
	}

	@Bean(name = TECHNICAL_HELPER_USER_DECORATOR)
	public UserDecorator technicalHelperUserDecorator() {
		UserDecorator userDecorator = new UserDecorator("", "", mediaWikiSourcesProperties.getTechnicalHelper().getApiUrl());
		userDecorator.login();
		return userDecorator;
	}

}

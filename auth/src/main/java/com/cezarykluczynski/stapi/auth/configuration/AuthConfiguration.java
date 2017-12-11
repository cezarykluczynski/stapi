package com.cezarykluczynski.stapi.auth.configuration;

import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({AccountProperties.class, ApiKeyProperties.class})
@DependsOn("liquibase")
public class AuthConfiguration {

	@Inject
	private ConsentRepository consentRepository;

	@PostConstruct
	public void postContruct() {
		consentRepository.ensureExists();
	}

}

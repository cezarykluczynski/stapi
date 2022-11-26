package com.cezarykluczynski.stapi.auth.configuration;

import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@Profile(SpringProfile.AUTH)
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

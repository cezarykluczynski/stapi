package com.cezarykluczynski.stapi.server.github.configuration;

import com.cezarykluczynski.stapi.server.github.model.GitHubDTO;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.kohsuke.github.GitHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class GitHubConfiguration {

	@Bean
	public GitHub gitHub() {
		try {
			return GitHub.connectAnonymously();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean("gitHubCache")
	public Cache<String, GitHubDTO> gitHubCache() {
		return CacheBuilder.newBuilder()
				.expireAfterWrite(3L, TimeUnit.MINUTES)
				.build();
	}

}

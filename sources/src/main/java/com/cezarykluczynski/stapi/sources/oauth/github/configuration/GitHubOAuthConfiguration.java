package com.cezarykluczynski.stapi.sources.oauth.github.configuration;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("sourcesGitHubOAuthConfiguration")
@EnableConfigurationProperties({GitHubOAuthProperties.class})
public class GitHubOAuthConfiguration {

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}

}

package com.cezarykluczynski.stapi.sources.oauth.github.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = GitHubOAuthProperties.PREFIX)
public class GitHubOAuthProperties {

	public static final String PREFIX = "oauth.github";

	private String clientId;

	private String clientSecret;

}

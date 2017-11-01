package com.cezarykluczynski.stapi.auth.oauth.github.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = GitHubOAuthProperties.PREFIX)
public class GitHubOAuthProperties {

	public static final String PREFIX = "oauth.github";

	private String clientId;

	private String clientSecret;

	private List<Long> adminIdentifiers;

}

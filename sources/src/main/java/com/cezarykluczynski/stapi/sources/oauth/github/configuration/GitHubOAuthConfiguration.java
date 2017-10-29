package com.cezarykluczynski.stapi.sources.oauth.github.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("sourcesGitHubOAuthConfiguration")
@EnableConfigurationProperties({GitHubOAuthProperties.class})
public class GitHubOAuthConfiguration {
}

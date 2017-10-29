package com.cezarykluczynski.stapi.sources.oauth.github.service;

import com.cezarykluczynski.stapi.sources.oauth.github.configuration.GitHubOAuthProperties;
import org.springframework.stereotype.Service;

@Service
public class GitHubAdminDetector {

	private final GitHubOAuthProperties gitHubOAuthProperties;

	public GitHubAdminDetector(GitHubOAuthProperties gitHubOAuthProperties) {
		this.gitHubOAuthProperties = gitHubOAuthProperties;
	}

	public boolean isAdminId(Long id) {
		return gitHubOAuthProperties.getAdminIdentifiers().contains(id);
	}

}

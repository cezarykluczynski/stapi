package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.oauth.github.configuration.GitHubOAuthProperties;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class GitHubAdminDetector {

	private final GitHubOAuthProperties gitHubOAuthProperties;

	public GitHubAdminDetector(GitHubOAuthProperties gitHubOAuthProperties) {
		this.gitHubOAuthProperties = gitHubOAuthProperties;
	}

	public boolean isAdminId(Long id) {
		return gitHubOAuthProperties.getAdminIdentifiers().contains(id);
	}

}

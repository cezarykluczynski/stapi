package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.oauth.github.configuration.GitHubOAuthProperties;
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
class GitHubOAuthUrlFactory {

	private static final String GITHUB_CLIENT_ID_CANNOT_BE_NULL = "GitHub client_id cannot be null";
	private static final String GITHUB_CLIENT_SECRET_CANNOT_BE_NULL = "GitHub client_secret cannot be null";

	private final GitHubOAuthProperties gitHubOAuthProperties;

	GitHubOAuthUrlFactory(GitHubOAuthProperties gitHubOAuthProperties) {
		this.gitHubOAuthProperties = gitHubOAuthProperties;
	}

	GitHubRedirectUrlDTO createGitHubOAuthorizeUrl() {
		String clientId = gitHubOAuthProperties.getClientId();
		Preconditions.checkNotNull(clientId, GITHUB_CLIENT_ID_CANNOT_BE_NULL);
		return new GitHubRedirectUrlDTO("https://github.com/login/oauth/authorize?client_id=" + clientId);
	}

	GitHubRedirectUrlDTO createAccessTokenUrl(String code) {
		String clientId = gitHubOAuthProperties.getClientId();
		Preconditions.checkNotNull(clientId, GITHUB_CLIENT_ID_CANNOT_BE_NULL);
		String clientSecret = gitHubOAuthProperties.getClientSecret();
		Preconditions.checkNotNull(clientSecret, GITHUB_CLIENT_SECRET_CANNOT_BE_NULL);
		return new GitHubRedirectUrlDTO("https://github.com/login/oauth/access_token?client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&code=" + code);
	}

	GitHubRedirectUrlDTO createUserUrl(String accessToken) {
		return new GitHubRedirectUrlDTO("https://api.github.com/user?access_token=" + accessToken);
	}

}

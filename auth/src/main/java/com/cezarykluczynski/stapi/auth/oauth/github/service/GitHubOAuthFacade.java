package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthFacade {

	private final GitHubOAuthUrlFactory gitHubOAuthUrlFactory;

	private final GitHubOAuthAuthenticationService gitHubOAuthAuthenticationService;

	public GitHubOAuthFacade(GitHubOAuthUrlFactory gitHubOAuthUrlFactory,
			GitHubOAuthAuthenticationService gitHubOAuthAuthenticationService) {
		this.gitHubOAuthUrlFactory = gitHubOAuthUrlFactory;
		this.gitHubOAuthAuthenticationService = gitHubOAuthAuthenticationService;
	}

	public GitHubRedirectUrlDTO getGitHubOAuthAuthorizeUrl() {
		return gitHubOAuthUrlFactory.createGitHubOAuthorizeUrl();
	}

	public GitHubRedirectUrlDTO authenticate(String code) {
		return gitHubOAuthAuthenticationService.authenticate(code);
	}

}

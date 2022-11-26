package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
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

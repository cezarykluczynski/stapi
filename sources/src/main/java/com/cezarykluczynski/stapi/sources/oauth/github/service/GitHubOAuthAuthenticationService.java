package com.cezarykluczynski.stapi.sources.oauth.github.service;

import com.cezarykluczynski.stapi.sources.common.service.UrlContentRetriever;
import com.cezarykluczynski.stapi.sources.oauth.github.dto.GitHubRedirectUrlDTO;
import com.cezarykluczynski.stapi.sources.oauth.github.session.GitHubOAuthSessionCreator;
import org.springframework.stereotype.Service;

@Service
class GitHubOAuthAuthenticationService {

	private final GitHubOAuthUrlFactory gitHubOAuthUrlFactory;

	private final UrlContentRetriever urlContentRetriever;

	private final GitHubAccessTokenExtractor gitHubAccessTokenExtractor;

	private final GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory;

	private final GitHubOAuthSessionCreator gitHubOAuthSessionCreator;

	GitHubOAuthAuthenticationService(GitHubOAuthUrlFactory gitHubOAuthUrlFactory,
			UrlContentRetriever urlContentRetriever, GitHubAccessTokenExtractor gitHubAccessTokenExtractor,
			GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory, GitHubOAuthSessionCreator gitHubOAuthSessionCreator) {
		this.gitHubOAuthUrlFactory = gitHubOAuthUrlFactory;
		this.urlContentRetriever = urlContentRetriever;
		this.gitHubAccessTokenExtractor = gitHubAccessTokenExtractor;
		this.gitHubUserDetailsDTOFactory = gitHubUserDetailsDTOFactory;
		this.gitHubOAuthSessionCreator = gitHubOAuthSessionCreator;
	}

	GitHubRedirectUrlDTO authenticate(String code) {
		doAuthenticate(code);
		return new GitHubRedirectUrlDTO("/panel");
	}

	private void doAuthenticate(String code) {
		String accessTokenResponseBody = urlContentRetriever.getBody(gitHubOAuthUrlFactory.createAccessTokenUrl(code).getUrl());

		if (accessTokenResponseBody == null) {
			return;
		}

		String accessToken = gitHubAccessTokenExtractor.extract(accessTokenResponseBody);
		if (accessToken == null) {
			return;
		}

		String userResponseBody = urlContentRetriever.getBody(gitHubOAuthUrlFactory.createUserUrl(accessToken).getUrl());

		if (userResponseBody == null) {
			return;
		}

		gitHubOAuthSessionCreator.create(gitHubUserDetailsDTOFactory.create(userResponseBody));
	}

}

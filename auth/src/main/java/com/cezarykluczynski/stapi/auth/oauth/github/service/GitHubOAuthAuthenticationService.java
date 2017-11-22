package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.GitHubOAuthSessionCreator;
import com.cezarykluczynski.stapi.sources.common.service.UrlContentRetriever;
import org.springframework.stereotype.Service;

@Service
class GitHubOAuthAuthenticationService {

	private final GitHubOAuthUrlFactory gitHubOAuthUrlFactory;

	private final UrlContentRetriever urlContentRetriever;

	private final GitHubAccessTokenExtractor gitHubAccessTokenExtractor;

	private final GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory;

	private final GitHubOAuthSessionCreator gitHubOAuthSessionCreator;

	private final AccountApi accountApi;

	GitHubOAuthAuthenticationService(GitHubOAuthUrlFactory gitHubOAuthUrlFactory,
			UrlContentRetriever urlContentRetriever, GitHubAccessTokenExtractor gitHubAccessTokenExtractor,
			GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory, GitHubOAuthSessionCreator gitHubOAuthSessionCreator, AccountApi accountApi) {
		this.gitHubOAuthUrlFactory = gitHubOAuthUrlFactory;
		this.urlContentRetriever = urlContentRetriever;
		this.gitHubAccessTokenExtractor = gitHubAccessTokenExtractor;
		this.gitHubUserDetailsDTOFactory = gitHubUserDetailsDTOFactory;
		this.gitHubOAuthSessionCreator = gitHubOAuthSessionCreator;
		this.accountApi = accountApi;
	}

	GitHubRedirectUrlDTO authenticate(String code) {
		doAuthenticate(code);
		return new GitHubRedirectUrlDTO("/panel");
	}

	private void doAuthenticate(String code) {
		String accessTokenResponseBody = urlContentRetriever.getBody(gitHubOAuthUrlFactory.createAccessTokenUrl(code).getUrl());

		if (accessTokenResponseBody != null) {
			String accessToken = gitHubAccessTokenExtractor.extract(accessTokenResponseBody);
			if (accessToken != null) {
				String userResponseBody = urlContentRetriever.getBody(gitHubOAuthUrlFactory.createUserUrl(accessToken).getUrl());
				if (userResponseBody != null) {
					gitHubOAuthSessionCreator.create(accountApi.ensureExists(gitHubUserDetailsDTOFactory.create(userResponseBody)));
				}
			}
		}
	}

}

package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO;
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.GitHubOAuthSessionCreator;
import com.cezarykluczynski.stapi.sources.common.service.UrlContentRetriever;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import org.springframework.stereotype.Service;

@Service
class GitHubOAuthAuthenticationService {

	private final GitHubOAuthUrlFactory gitHubOAuthUrlFactory;

	private final UrlContentRetriever urlContentRetriever;

	private final GitHubAccessTokenExtractor gitHubAccessTokenExtractor;

	private final GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory;

	private final FeatureSwitchApi featureSwitchApi;

	private final GitHubAdminDetector gitHubAdminDetector;

	private final GitHubOAuthSessionCreator gitHubOAuthSessionCreator;

	private final AccountApi accountApi;

	@SuppressWarnings("ParameterNumber")
	GitHubOAuthAuthenticationService(GitHubOAuthUrlFactory gitHubOAuthUrlFactory,
			UrlContentRetriever urlContentRetriever, GitHubAccessTokenExtractor gitHubAccessTokenExtractor,
			GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory, FeatureSwitchApi featureSwitchApi, GitHubAdminDetector gitHubAdminDetector,
			GitHubOAuthSessionCreator gitHubOAuthSessionCreator, AccountApi accountApi) {
		this.gitHubOAuthUrlFactory = gitHubOAuthUrlFactory;
		this.urlContentRetriever = urlContentRetriever;
		this.gitHubAccessTokenExtractor = gitHubAccessTokenExtractor;
		this.gitHubUserDetailsDTOFactory = gitHubUserDetailsDTOFactory;
		this.featureSwitchApi = featureSwitchApi;
		this.gitHubAdminDetector = gitHubAdminDetector;
		this.gitHubOAuthSessionCreator = gitHubOAuthSessionCreator;
		this.accountApi = accountApi;
	}

	GitHubRedirectUrlDTO authenticate(String code) {
		ensureUserPanelEnabled();
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
					GitHubUserDetailsDTO gitHubUserDetailsDTO = gitHubUserDetailsDTOFactory.create(userResponseBody);

					if (!featureSwitchApi.isEnabled(FeatureSwitchType.ADMIN_PANEL) && !gitHubAdminDetector.isAdminId(gitHubUserDetailsDTO.getId())) {
						throw new StapiRuntimeException("Cannot create account while panel is disabled");
					}

					gitHubOAuthSessionCreator.create(accountApi.ensureExists(gitHubUserDetailsDTO));
				}
			}
		}
	}

	private void ensureUserPanelEnabled() {
		if (!featureSwitchApi.isEnabled(FeatureSwitchType.USER_PANEL)) {
			throw new StapiRuntimeException("Authentication not possible when user panel is disabled");
		}
	}

}

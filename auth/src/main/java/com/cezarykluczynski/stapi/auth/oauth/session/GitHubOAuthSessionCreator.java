package com.cezarykluczynski.stapi.auth.oauth.session;

import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubAdminDetector;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.constant.ApplicationPermission;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthSessionCreator {

	private final OAuthSessionHolder oauthSessionHolder;

	private final GitHubAdminDetector gitHubAdminDetector;

	public GitHubOAuthSessionCreator(OAuthSessionHolder oauthSessionHolder, GitHubAdminDetector gitHubAdminDetector) {
		this.oauthSessionHolder = oauthSessionHolder;
		this.gitHubAdminDetector = gitHubAdminDetector;
	}

	public void create(Account account) {
		@SuppressWarnings("LocalVariableName")
		OAuthSession oAuthSession = new OAuthSession();
		oAuthSession.setAccountId(account.getId());
		oAuthSession.setGitHubId(account.getGitHubUserId());
		oAuthSession.setGitHubName(account.getName());
		oAuthSession.getPermissions().add(ApplicationPermission.API_KEY_MANAGEMENT);
		if (gitHubAdminDetector.isAdminId(oAuthSession.getGitHubId())) {
			oAuthSession.getPermissions().add(ApplicationPermission.ADMIN_MANAGEMENT);
		}
		oauthSessionHolder.setOAuthSession(oAuthSession);
	}

}

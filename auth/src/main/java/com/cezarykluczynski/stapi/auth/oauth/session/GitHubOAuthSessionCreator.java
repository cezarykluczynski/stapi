package com.cezarykluczynski.stapi.auth.oauth.session;

import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubAdminDetector;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.constant.ApplicationPermission;
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthSessionCreator {

	private final OAuthSessionHolder oauthSessionHolder;

	private final GitHubAdminDetector gitHubAdminDetector;

	private final FeatureSwitchApi featureSwitchApi;

	public GitHubOAuthSessionCreator(OAuthSessionHolder oauthSessionHolder, GitHubAdminDetector gitHubAdminDetector,
			FeatureSwitchApi featureSwitchApi) {
		this.oauthSessionHolder = oauthSessionHolder;
		this.gitHubAdminDetector = gitHubAdminDetector;
		this.featureSwitchApi = featureSwitchApi;
	}

	public void create(Account account) {
		@SuppressWarnings("LocalVariableName")
		OAuthSession oAuthSession = new OAuthSession();
		oAuthSession.setAccountId(account.getId());
		oAuthSession.setGitHubId(account.getGitHubUserId());
		oAuthSession.setGitHubName(account.getName());
		if (featureSwitchApi.isEnabled(FeatureSwitchType.USER_PANEL)) {
			oAuthSession.getPermissions().add(ApplicationPermission.API_KEY_MANAGEMENT);
		}
		if (featureSwitchApi.isEnabled(FeatureSwitchType.ADMIN_PANEL) && gitHubAdminDetector.isAdminId(oAuthSession.getGitHubId())) {
			oAuthSession.getPermissions().add(ApplicationPermission.ADMIN_MANAGEMENT);
		}
		oauthSessionHolder.setOAuthSession(oAuthSession);
	}

}

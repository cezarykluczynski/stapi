package com.cezarykluczynski.stapi.auth.oauth.session

import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubAdminDetector
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.util.constant.ApplicationPermission
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType
import com.google.common.collect.Lists
import spock.lang.Specification

class GitHubOAuthSessionCreatorTest extends Specification {

	private static final Long ACCOUNT_ID = 6L
	private static final Long GITHUB_ID = 11L
	private static final String NAME = 'NAME'

	private OAuthSessionHolder oauthSessionHolderMock

	private GitHubAdminDetector gitHubAdminDetectorMock

	private FeatureSwitchApi featureSwitchApiMock

	private GitHubOAuthSessionCreator gitHubOAuthSessionCreator

	void setup() {
		oauthSessionHolderMock = Mock()
		gitHubAdminDetectorMock = Mock()
		featureSwitchApiMock = Mock()
		gitHubOAuthSessionCreator = new GitHubOAuthSessionCreator(oauthSessionHolderMock, gitHubAdminDetectorMock, featureSwitchApiMock)
	}

	void "account data is stored in session, without user privileges"() {
		given:
		Account account = new Account(id: ACCOUNT_ID, gitHubUserId: GITHUB_ID, name: NAME)

		when:
		gitHubOAuthSessionCreator.create(account)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> false
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> false
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.accountId == ACCOUNT_ID
			assert oAuthSession.gitHubId == GITHUB_ID
			assert oAuthSession.gitHubName == NAME
			assert oAuthSession.permissions == []
		}
		0 * _
	}

	void "account data is stored in session, without admin privileges"() {
		given:
		Account account = new Account(id: ACCOUNT_ID, gitHubUserId: GITHUB_ID, name: NAME)

		when:
		gitHubOAuthSessionCreator.create(account)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> false
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.accountId == ACCOUNT_ID
			assert oAuthSession.gitHubId == GITHUB_ID
			assert oAuthSession.gitHubName == NAME
			assert oAuthSession.permissions == Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT)
		}
		0 * _
	}

	void "account data is stored in session, and with admin privileges"() {
		given:
		Account account = new Account(id: ACCOUNT_ID, gitHubUserId: GITHUB_ID, name: NAME)

		when:
		gitHubOAuthSessionCreator.create(account)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> true
		1 * gitHubAdminDetectorMock.isAdminId(GITHUB_ID) >> true
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.accountId == ACCOUNT_ID
			assert oAuthSession.gitHubId == GITHUB_ID
			assert oAuthSession.gitHubName == NAME
			assert oAuthSession.permissions == Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT, ApplicationPermission.ADMIN_MANAGEMENT)
		}
		0 * _
	}

}

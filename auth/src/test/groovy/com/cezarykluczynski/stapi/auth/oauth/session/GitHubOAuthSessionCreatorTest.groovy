package com.cezarykluczynski.stapi.auth.oauth.session

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubAdminDetector
import com.cezarykluczynski.stapi.util.constant.ApplicationPermission
import com.google.common.collect.Lists
import spock.lang.Specification

class GitHubOAuthSessionCreatorTest extends Specification {

	private static final String LOGIN = 'LOGIN'
	private static final Long ID = 11L
	private static final String NAME = 'NAME'

	private OAuthSessionHolder oauthSessionHolderMock

	private GitHubAdminDetector gitHubAdminDetectorMock

	private GitHubOAuthSessionCreator gitHubOAuthSessionCreator

	void setup() {
		oauthSessionHolderMock = Mock()
		gitHubAdminDetectorMock = Mock()
		gitHubOAuthSessionCreator = new GitHubOAuthSessionCreator(oauthSessionHolderMock, gitHubAdminDetectorMock)
	}

	void "GitHub user data is stored in session, without user name and without admin privileges"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN)

		when:
		gitHubOAuthSessionCreator.create(gitHubUserDetailsDTO)

		then:
		1 * gitHubAdminDetectorMock.isAdminId(ID) >> false
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.gitHubId == ID
			assert oAuthSession.gitHubName == LOGIN
			assert oAuthSession.permissions == Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT)
		}
		0 * _
	}

	void "GitHub user data is stored in session, with user name, and without admin privileges"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN, name: NAME)

		when:
		gitHubOAuthSessionCreator.create(gitHubUserDetailsDTO)

		then:
		1 * gitHubAdminDetectorMock.isAdminId(ID) >> false
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.gitHubId == ID
			assert oAuthSession.gitHubName == NAME
			assert oAuthSession.permissions == Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT)
		}
		0 * _
	}

	void "GitHub user data is stored in session, with user name, and with admin privileges"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN, name: NAME)

		when:
		gitHubOAuthSessionCreator.create(gitHubUserDetailsDTO)

		then:
		1 * gitHubAdminDetectorMock.isAdminId(ID) >> true
		1 * oauthSessionHolderMock.setOAuthSession(_ as OAuthSession) >> { OAuthSession oAuthSession ->
			assert oAuthSession.gitHubId == ID
			assert oAuthSession.gitHubName == NAME
			assert oAuthSession.permissions == Lists.newArrayList(ApplicationPermission.API_KEY_MANAGEMENT, ApplicationPermission.ADMIN_MANAGEMENT)
		}
		0 * _
	}

}

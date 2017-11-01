package com.cezarykluczynski.stapi.auth.oauth.session

import com.google.common.collect.Lists
import spock.lang.Specification

class OAuthSessionTest extends Specification {

	private static final Long ID = 11L
	private static final String NAME = 'NAME'
	private static final String PERMISSION = 'PERMISSION'

	void "creates copy"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: ID, gitHubName: NAME, permissions: Lists.newArrayList(PERMISSION))

		when:
		OAuthSession oAuthSessionCopy = oAuthSession.copy()

		then:
		oAuthSessionCopy.gitHubId == oAuthSession.gitHubId
		oAuthSessionCopy.gitHubName == oAuthSession.gitHubName
		oAuthSessionCopy.permissions[0] == oAuthSession.permissions[0]

		when:
		oAuthSession.gitHubId = null
		oAuthSession.gitHubName = null
		oAuthSession.permissions = Lists.newArrayList()

		then:
		oAuthSession.gitHubId == null
		oAuthSession.gitHubName == null
		oAuthSession.permissions == Lists.newArrayList()

		then:
		oAuthSessionCopy.gitHubId == ID
		oAuthSessionCopy.gitHubName == NAME
		oAuthSessionCopy.permissions[0] == PERMISSION
	}

}

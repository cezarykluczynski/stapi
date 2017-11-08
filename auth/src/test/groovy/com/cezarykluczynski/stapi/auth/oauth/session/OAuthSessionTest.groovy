package com.cezarykluczynski.stapi.auth.oauth.session

import com.google.common.collect.Lists
import spock.lang.Specification

class OAuthSessionTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long GITHUB_ID = 11L
	private static final String NAME = 'NAME'
	private static final String PERMISSION = 'PERMISSION'

	void "creates copy"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(
				accountId: ACCOUNT_ID,
				gitHubId: GITHUB_ID,
				gitHubName: NAME,
				permissions: Lists.newArrayList(PERMISSION))

		when:
		OAuthSession oAuthSessionCopy = oAuthSession.copy()

		then:
		oAuthSessionCopy.accountId == oAuthSession.accountId
		oAuthSessionCopy.gitHubId == oAuthSession.gitHubId
		oAuthSessionCopy.gitHubName == oAuthSession.gitHubName
		oAuthSessionCopy.permissions[0] == oAuthSession.permissions[0]

		when:
		oAuthSession.accountId = null
		oAuthSession.gitHubId = null
		oAuthSession.gitHubName = null
		oAuthSession.permissions = Lists.newArrayList()

		then:
		oAuthSession.accountId == null
		oAuthSession.gitHubId == null
		oAuthSession.gitHubName == null
		oAuthSession.permissions == Lists.newArrayList()

		then:
		oAuthSessionCopy.accountId == ACCOUNT_ID
		oAuthSessionCopy.gitHubId == GITHUB_ID
		oAuthSessionCopy.gitHubName == NAME
		oAuthSessionCopy.permissions[0] == PERMISSION
	}

}

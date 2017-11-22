package com.cezarykluczynski.stapi.auth.oauth.session

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import spock.lang.Specification

class OAuthSessionHolderTest extends Specification {

	private static final Long ID = 11L
	private static final String NAME = 'NAME'
	private static final String PERMISSION = 'PERMISSION'

	private OAuthSessionHolder oAuthSessionHolder

	void setup() {
		oAuthSessionHolder = new OAuthSessionHolder()
	}

	void "set OAuthSession, then returns nullable copy of it"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: ID, gitHubName: NAME, permissions: Lists.newArrayList(PERMISSION))
		oAuthSessionHolder.setOAuthSession(oAuthSession)

		when:
		OAuthSession oAuthSessionNullableCopy = oAuthSessionHolder.nullableOAuthSession

		then:
		oAuthSessionNullableCopy.gitHubId == oAuthSession.gitHubId
		oAuthSessionNullableCopy.gitHubName == oAuthSession.gitHubName
		oAuthSessionNullableCopy.permissions[0] == oAuthSession.permissions[0]

		when:
		oAuthSession.gitHubId = null
		oAuthSession.gitHubName = null
		oAuthSession.permissions = Lists.newArrayList()

		then:
		oAuthSession.gitHubId == null
		oAuthSession.gitHubName == null
		oAuthSession.permissions == Lists.newArrayList()

		then:
		oAuthSessionNullableCopy.gitHubId == ID
		oAuthSessionNullableCopy.gitHubName == NAME
		oAuthSessionNullableCopy.permissions[0] == PERMISSION
	}

	void "set OAuthSession, then returns copy of it"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: ID, gitHubName: NAME, permissions: Lists.newArrayList(PERMISSION))
		oAuthSessionHolder.setOAuthSession(oAuthSession)

		when:
		OAuthSession oAuthSessionNullableCopy = oAuthSessionHolder.OAuthSession

		then:
		oAuthSessionNullableCopy.gitHubId == oAuthSession.gitHubId
		oAuthSessionNullableCopy.gitHubName == oAuthSession.gitHubName
		oAuthSessionNullableCopy.permissions[0] == oAuthSession.permissions[0]

		when:
		oAuthSession.gitHubId = null
		oAuthSession.gitHubName = null
		oAuthSession.permissions = Lists.newArrayList()

		then:
		oAuthSession.gitHubId == null
		oAuthSession.gitHubName == null
		oAuthSession.permissions == Lists.newArrayList()

		then:
		oAuthSessionNullableCopy.gitHubId == ID
		oAuthSessionNullableCopy.gitHubName == NAME
		oAuthSessionNullableCopy.permissions[0] == PERMISSION
	}

	void "returns null when no OAuthSession has been set"() {
		when:
		OAuthSession oAuthSessionCopy = oAuthSessionHolder.nullableOAuthSession

		then:
		oAuthSessionCopy == null
	}

	void "throws exception when no OAuthSession has been set"() {
		when:
		oAuthSessionHolder.OAuthSession

		then:
		thrown(StapiRuntimeException)
	}

	void "session can be removed"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: ID, gitHubName: NAME, permissions: Lists.newArrayList(PERMISSION))
		oAuthSessionHolder.setOAuthSession(oAuthSession)

		when:
		oAuthSessionHolder.remove()

		then:
		oAuthSessionHolder.nullableOAuthSession == null
	}

}

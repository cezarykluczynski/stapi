package com.cezarykluczynski.stapi.auth.oauth.github.service

import com.cezarykluczynski.stapi.auth.oauth.github.configuration.GitHubOAuthProperties
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO
import spock.lang.Specification

@SuppressWarnings('BuilderMethodWithSideEffects')
class GitHubOAuthUrlFactoryTest extends Specification {

	private static final String CODE = 'CODE'
	private static final String CLIENT_ID = 'CLIENT_ID'
	private static final String CLIENT_SECRET = 'CLIENT_SECRET'
	private static final String ACCESS_TOKEN = 'ACCESS_TOKEN'

	private GitHubOAuthProperties gitHubOAuthPropertiesMock

	private GitHubOAuthUrlFactory gitHubOAuthUrlFactory

	void setup() {
		gitHubOAuthPropertiesMock = Mock()
		gitHubOAuthUrlFactory = new GitHubOAuthUrlFactory(gitHubOAuthPropertiesMock)
	}

	void "createGitHubOAuthorizeUrl requires non-null client_id"() {
		when:
		gitHubOAuthUrlFactory.createGitHubOAuthorizeUrl()

		then:
		1 * gitHubOAuthPropertiesMock.clientId >> null
		0 * _
		thrown(NullPointerException)
	}

	void "createGitHubOAuthorizeUrl returns URL when client_id is not null"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthUrlFactory.createGitHubOAuthorizeUrl()

		then:
		1 * gitHubOAuthPropertiesMock.clientId >> CLIENT_ID
		0 * _
		gitHubRedirectUrlDTO.url == "https://github.com/login/oauth/authorize?client_id=${CLIENT_ID}"
	}

	void "createAccessTokenUrl requires non-null client_id"() {
		when:
		gitHubOAuthUrlFactory.createAccessTokenUrl(CODE)

		then:
		1 * gitHubOAuthPropertiesMock.clientId >> null
		0 * _
		thrown(NullPointerException)
	}

	void "createAccessTokenUrl requires non-null client_secret"() {
		when:
		gitHubOAuthUrlFactory.createAccessTokenUrl(CODE)

		then:
		1 * gitHubOAuthPropertiesMock.clientId >> CLIENT_ID
		1 * gitHubOAuthPropertiesMock.clientSecret >> null
		0 * _
		thrown(NullPointerException)
	}

	void "createAccessTokenUrl returns URL when client_id and client_secret are not null"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthUrlFactory.createAccessTokenUrl(CODE)

		then:
		1 * gitHubOAuthPropertiesMock.clientId >> CLIENT_ID
		1 * gitHubOAuthPropertiesMock.clientSecret >> CLIENT_SECRET
		0 * _
		gitHubRedirectUrlDTO.url == "https://github.com/login/oauth/access_token?client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&code=${CODE}"
	}

	void "createUserUrl creates URL"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthUrlFactory.createUserUrl(ACCESS_TOKEN)

		then:
		0 * _
		gitHubRedirectUrlDTO.url == "https://api.github.com/user?access_token=${ACCESS_TOKEN}"
	}

}

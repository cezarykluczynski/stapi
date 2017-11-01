package com.cezarykluczynski.stapi.auth.oauth.github.service

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO
import spock.lang.Specification

class GitHubOAuthFacadeTest extends Specification {

	private static final String CODE = 'CODE'

	private GitHubOAuthUrlFactory gitHubOAuthUrlFactoryMock

	private GitHubOAuthAuthenticationService gitHubOAuthAuthenticationServiceMock

	private GitHubOAuthFacade gitHubOAuthFacade

	void setup() {
		gitHubOAuthUrlFactoryMock = Mock()
		gitHubOAuthAuthenticationServiceMock = Mock()
		gitHubOAuthFacade = new GitHubOAuthFacade(gitHubOAuthUrlFactoryMock, gitHubOAuthAuthenticationServiceMock)
	}

	void "authorize URL is retrieved from GitHubOAuthUrlFactory"() {
		given:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = Mock()

		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTOOutput = gitHubOAuthFacade.gitHubOAuthAuthorizeUrl

		then:
		1 * gitHubOAuthUrlFactoryMock.createGitHubOAuthorizeUrl() >> gitHubRedirectUrlDTO
		0 * _
		gitHubRedirectUrlDTOOutput == gitHubRedirectUrlDTO
	}

	void "authentication is performed in GitHubOAuthAuthenticationService"() {
		given:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = Mock()

		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTOOutput = gitHubOAuthFacade.authenticate(CODE)

		then:
		1 * gitHubOAuthAuthenticationServiceMock.authenticate(CODE) >> gitHubRedirectUrlDTO
		0 * _
		gitHubRedirectUrlDTOOutput == gitHubRedirectUrlDTO
	}

}

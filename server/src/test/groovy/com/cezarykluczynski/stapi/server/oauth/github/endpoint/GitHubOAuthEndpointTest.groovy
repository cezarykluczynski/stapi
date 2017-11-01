package com.cezarykluczynski.stapi.server.oauth.github.endpoint

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO
import com.cezarykluczynski.stapi.auth.oauth.github.service.GitHubOAuthFacade
import spock.lang.Specification

import javax.ws.rs.core.Response

class GitHubOAuthEndpointTest extends Specification {

	private static final String CODE = 'CODE'
	private static final String URL = 'URL'

	private GitHubOAuthFacade gitHubOAuthFacadeMock

	private GitHubOAuthEndpoint gitHubOAuthEndpoint

	void setup() {
		gitHubOAuthFacadeMock = Mock()
		gitHubOAuthEndpoint = new GitHubOAuthEndpoint(gitHubOAuthFacadeMock)
	}

	void "gets github OAuth authorize url"() {
		given:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = Mock()

		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTOOutput = gitHubOAuthEndpoint.gitHubOAuthAuthorizeUrl

		then:
		1 * gitHubOAuthFacadeMock.gitHubOAuthAuthorizeUrl >> gitHubRedirectUrlDTO
		0 * _
		gitHubRedirectUrlDTOOutput == gitHubRedirectUrlDTO
	}

	void "authenticates and redirects"() {
		given:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = Mock()

		when:
		Response response = gitHubOAuthEndpoint.authenticate(CODE)

		then:
		1 * gitHubOAuthFacadeMock.authenticate(CODE) >> gitHubRedirectUrlDTO
		1 * gitHubRedirectUrlDTO.url >> URL
		0 * _
		response.status == 302
		response.headers.get('Location')[0] == URL
	}

}

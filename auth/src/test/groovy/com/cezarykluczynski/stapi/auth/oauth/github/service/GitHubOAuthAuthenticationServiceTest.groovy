package com.cezarykluczynski.stapi.auth.oauth.github.service

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubRedirectUrlDTO
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import com.cezarykluczynski.stapi.auth.oauth.session.GitHubOAuthSessionCreator
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.sources.common.service.UrlContentRetriever
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType
import spock.lang.Specification

class GitHubOAuthAuthenticationServiceTest extends Specification {

	private static final String CODE = 'CODE'
	private static final String ACCESS_TOKEN_URL = 'ACCESS_TOKEN_URL'
	private static final String ACCESS_TOKEN_RESPONSE_BODY = 'ACCESS_TOKEN_RESPONSE_BODY'
	private static final String ACCESS_TOKEN = 'ACCESS_TOKEN'
	private static final String USER_URL = 'USER_URL'
	private static final String USER_RESPONSE_BODY = 'USER_RESPONSE_BODY'
	private static final Long GITHUB_ID = 10L

	private GitHubOAuthUrlFactory gitHubOAuthUrlFactoryMock

	private UrlContentRetriever urlContentRetrieverMock

	private GitHubAccessTokenExtractor gitHubAccessTokenExtractorMock

	private GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactoryMock

	private FeatureSwitchApi featureSwitchApiMock

	private GitHubAdminDetector gitHubAdminDetectorMock

	private GitHubOAuthSessionCreator gitHubOAuthSessionCreatorMock

	private AccountApi accountApiMock

	private GitHubOAuthAuthenticationService gitHubOAuthAuthenticationService

	void setup() {
		gitHubOAuthUrlFactoryMock = Mock()
		urlContentRetrieverMock = Mock()
		gitHubAccessTokenExtractorMock = Mock()
		gitHubUserDetailsDTOFactoryMock = Mock()
		featureSwitchApiMock = Mock()
		gitHubAdminDetectorMock = Mock()
		gitHubOAuthSessionCreatorMock = Mock()
		accountApiMock = Mock()
		gitHubOAuthAuthenticationService = new GitHubOAuthAuthenticationService(gitHubOAuthUrlFactoryMock, urlContentRetrieverMock,
				gitHubAccessTokenExtractorMock, gitHubUserDetailsDTOFactoryMock, featureSwitchApiMock, gitHubAdminDetectorMock,
				gitHubOAuthSessionCreatorMock, accountApiMock)
	}

	void "when user panel is disabled, authentication is not possible"() {
		when:
		gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> false
		0 * _
		thrown(StapiRuntimeException)
	}

	void "when access token body cannot be retrieved, authentication is not continued"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * gitHubOAuthUrlFactoryMock.createAccessTokenUrl(CODE) >> new GitHubRedirectUrlDTO(ACCESS_TOKEN_URL)
		1 * urlContentRetrieverMock.getBody(ACCESS_TOKEN_URL) >> null
		0 * _
		gitHubRedirectUrlDTO.url == '/panel'
	}

	void "when access token cannot be retrieved, authentication is not continued"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * gitHubOAuthUrlFactoryMock.createAccessTokenUrl(CODE) >> new GitHubRedirectUrlDTO(ACCESS_TOKEN_URL)
		1 * urlContentRetrieverMock.getBody(ACCESS_TOKEN_URL) >> ACCESS_TOKEN_RESPONSE_BODY
		1 * gitHubAccessTokenExtractorMock.extract(ACCESS_TOKEN_RESPONSE_BODY) >> null
		0 * _
		gitHubRedirectUrlDTO.url == '/panel'
	}

	void "when used cannot be retrieved with access token, authentication is not continued"() {
		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * gitHubOAuthUrlFactoryMock.createAccessTokenUrl(CODE) >> new GitHubRedirectUrlDTO(ACCESS_TOKEN_URL)
		1 * urlContentRetrieverMock.getBody(ACCESS_TOKEN_URL) >> ACCESS_TOKEN_RESPONSE_BODY
		1 * gitHubAccessTokenExtractorMock.extract(ACCESS_TOKEN_RESPONSE_BODY) >> ACCESS_TOKEN
		1 * gitHubOAuthUrlFactoryMock.createUserUrl(ACCESS_TOKEN) >> new GitHubRedirectUrlDTO(USER_URL)
		1 * urlContentRetrieverMock.getBody(USER_URL) >> null
		0 * _
		gitHubRedirectUrlDTO.url == '/panel'
	}

	void "when user is not admin, and panel is disabled, exception is thrown"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = Mock()

		when:
		gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * gitHubOAuthUrlFactoryMock.createAccessTokenUrl(CODE) >> new GitHubRedirectUrlDTO(ACCESS_TOKEN_URL)
		1 * urlContentRetrieverMock.getBody(ACCESS_TOKEN_URL) >> ACCESS_TOKEN_RESPONSE_BODY
		1 * gitHubAccessTokenExtractorMock.extract(ACCESS_TOKEN_RESPONSE_BODY) >> ACCESS_TOKEN
		1 * gitHubOAuthUrlFactoryMock.createUserUrl(ACCESS_TOKEN) >> new GitHubRedirectUrlDTO(USER_URL)
		1 * urlContentRetrieverMock.getBody(USER_URL) >> USER_RESPONSE_BODY
		1 * gitHubUserDetailsDTOFactoryMock.create(USER_RESPONSE_BODY) >> gitHubUserDetailsDTO
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> false
		1 * gitHubUserDetailsDTO.id >> GITHUB_ID
		1 * gitHubAdminDetectorMock.isAdminId(GITHUB_ID) >> false
		thrown(StapiRuntimeException)
	}

	void "authentication is performed"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = Mock()
		Account account = Mock()

		when:
		GitHubRedirectUrlDTO gitHubRedirectUrlDTO = gitHubOAuthAuthenticationService.authenticate(CODE)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.USER_PANEL) >> true
		1 * gitHubOAuthUrlFactoryMock.createAccessTokenUrl(CODE) >> new GitHubRedirectUrlDTO(ACCESS_TOKEN_URL)
		1 * urlContentRetrieverMock.getBody(ACCESS_TOKEN_URL) >> ACCESS_TOKEN_RESPONSE_BODY
		1 * gitHubAccessTokenExtractorMock.extract(ACCESS_TOKEN_RESPONSE_BODY) >> ACCESS_TOKEN
		1 * gitHubOAuthUrlFactoryMock.createUserUrl(ACCESS_TOKEN) >> new GitHubRedirectUrlDTO(USER_URL)
		1 * urlContentRetrieverMock.getBody(USER_URL) >> USER_RESPONSE_BODY
		1 * gitHubUserDetailsDTOFactoryMock.create(USER_RESPONSE_BODY) >> gitHubUserDetailsDTO
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.ADMIN_PANEL) >> true
		1 * accountApiMock.ensureExists(gitHubUserDetailsDTO) >> account
		1 * gitHubOAuthSessionCreatorMock.create(account)
		0 * _
		gitHubRedirectUrlDTO.url == '/panel'
	}

}

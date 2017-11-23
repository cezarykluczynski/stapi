package com.cezarykluczynski.stapi.auth.account.mapper

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import spock.lang.Specification

class UserDTOMapperTest extends Specification {

	private static final Long GITHUB_ID = 10L
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'
	private static final String PERMISSION_1 = 'PERMISSION_1'
	private static final String PERMISSION_2 = 'PERMISSION_2'

	private AccountApi accountApiMock

	private UserDTOMapper userDTOMapper

	void setup() {
		accountApiMock = Mock()
		userDTOMapper = new UserDTOMapper(accountApiMock)
	}

	void "maps OAuthSession to UserDTO"() {
		given:
		Account account = new Account(name: NAME, email: EMAIL)
		OAuthSession oAuthSession = new OAuthSession(
				gitHubId: GITHUB_ID,
				permissions: Lists.newArrayList(PERMISSION_1, PERMISSION_2))

		when:
		UserDTO userDTO = userDTOMapper.map(oAuthSession)

		then:
		1 * accountApiMock.findByGitHubUserId(GITHUB_ID) >> Optional.of(account)
		0 * _
		userDTO.name == NAME
		userDTO.email == EMAIL
		userDTO.permissions.size() == 2
		userDTO.permissions.contains PERMISSION_1
		userDTO.permissions.contains PERMISSION_2
	}

	void "throws exception when account was not found"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: GITHUB_ID)

		when:
		userDTOMapper.map(oAuthSession)

		then:
		1 * accountApiMock.findByGitHubUserId(GITHUB_ID) >> Optional.empty()
		0 * _
		thrown(StapiRuntimeException)
	}

}

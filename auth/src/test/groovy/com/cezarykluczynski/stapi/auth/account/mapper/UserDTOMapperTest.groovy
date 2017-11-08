package com.cezarykluczynski.stapi.auth.account.mapper

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.google.common.collect.Lists
import spock.lang.Specification

class UserDTOMapperTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String PERMISSION_1 = 'PERMISSION_1'
	private static final String PERMISSION_2 = 'PERMISSION_2'

	private UserDTOMapper userDTOMapper

	void setup() {
		userDTOMapper = new UserDTOMapper()
	}

	void "maps OAuthSession to UserDTO"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(
				gitHubName: NAME,
				permissions: Lists.newArrayList(PERMISSION_1, PERMISSION_2))

		when:
		UserDTO userDTO = userDTOMapper.map(oAuthSession)

		then:
		userDTO.name == NAME
		userDTO.permissions.size() == 2
		userDTO.permissions.contains PERMISSION_1
		userDTO.permissions.contains PERMISSION_2
	}

}

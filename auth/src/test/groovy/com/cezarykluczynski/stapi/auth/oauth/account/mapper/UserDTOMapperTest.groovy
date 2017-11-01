package com.cezarykluczynski.stapi.auth.oauth.account.mapper

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.auth.account.mapper.UserDTOMapper
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import spock.lang.Specification

class UserDTOMapperTest extends Specification {

	private static final String NAME = 'NAME'

	private UserDTOMapper userDTOMapper

	void setup() {
		userDTOMapper = new UserDTOMapper()
	}

	void "maps OAuthSession to UserDTO"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubName: NAME)

		when:
		UserDTO userDTO = userDTOMapper.map(oAuthSession)

		then:
		userDTO.name == NAME
	}

}

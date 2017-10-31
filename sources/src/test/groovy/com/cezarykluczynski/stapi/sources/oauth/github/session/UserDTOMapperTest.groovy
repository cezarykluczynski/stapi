package com.cezarykluczynski.stapi.sources.oauth.github.session

import com.cezarykluczynski.stapi.sources.oauth.github.dto.UserDTO
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

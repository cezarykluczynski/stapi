package com.cezarykluczynski.stapi.server.panel.service

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.auth.account.mapper.UserDTOMapper
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import spock.lang.Specification

class PanelCurrentUserProviderTest extends Specification {

	private OAuthSessionHolder oAuthSessionHolderMock

	private UserDTOMapper userDTOMapperMock

	private PanelCurrentUserProvider panelCurrentUserProvider

	void setup() {
		oAuthSessionHolderMock = Mock()
		userDTOMapperMock = Mock()
		panelCurrentUserProvider = new PanelCurrentUserProvider(oAuthSessionHolderMock, userDTOMapperMock)
	}

	void "when OAuthSession is found, it is mapped to UserDTO and returned"() {
		given:
		OAuthSession oAuthSession = Mock()
		UserDTO userDTO = Mock()

		when:
		UserDTO userDTOOutput = panelCurrentUserProvider.provide()

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * userDTOMapperMock.map(oAuthSession) >> userDTO
		0 * _
		userDTOOutput == userDTO
	}

}

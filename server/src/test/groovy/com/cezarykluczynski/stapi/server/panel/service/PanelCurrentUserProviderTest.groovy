package com.cezarykluczynski.stapi.server.panel.service

import com.cezarykluczynski.stapi.sources.oauth.github.dto.UserDTO
import com.cezarykluczynski.stapi.sources.oauth.github.session.OAuthSession
import com.cezarykluczynski.stapi.sources.oauth.github.session.OAuthSessionHolder
import com.cezarykluczynski.stapi.sources.oauth.github.session.UserDTOMapper
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
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

	void "when there is no OAuthSession, exception is thrown"() {
		when:
		panelCurrentUserProvider.provide()

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> null
		0 * _
		thrown(StapiRuntimeException)
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

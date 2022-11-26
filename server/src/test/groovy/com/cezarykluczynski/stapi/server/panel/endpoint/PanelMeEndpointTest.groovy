package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.server.panel.service.PanelCurrentUserProvider
import spock.lang.Specification

class PanelMeEndpointTest extends Specification {

	private PanelCurrentUserProvider panelCurrentUserProviderMock

	private PanelMeEndpoint panelMeEndpoint

	void setup() {
		panelCurrentUserProviderMock = Mock()
		panelMeEndpoint = new PanelMeEndpoint(panelCurrentUserProviderMock)
	}

	void "provides user details from PanelCurrentUserProvider"() {
		given:
		UserDTO userDTO = Mock()

		when:
		UserDTO me = panelMeEndpoint.me()

		then:
		1 * panelCurrentUserProviderMock.provide() >> userDTO
		0 * _
		me == userDTO
	}

}

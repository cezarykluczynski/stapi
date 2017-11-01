package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.server.panel.service.PanelCurrentUserProvider
import spock.lang.Specification

class PanelCommonEndpointTest extends Specification {

	private PanelCurrentUserProvider panelCurrentUserProviderMock

	private PanelCommonEndpoint panelCommonEndpoint

	void setup() {
		panelCurrentUserProviderMock = Mock()
		panelCommonEndpoint = new PanelCommonEndpoint(panelCurrentUserProviderMock)
	}

	void "provides user details from PanelCurrentUserProvider"() {
		given:
		UserDTO userDTO = Mock()

		when:
		UserDTO me = panelCommonEndpoint.me()

		then:
		1 * panelCurrentUserProviderMock.provide() >> userDTO
		0 * _
		me == userDTO
	}

}

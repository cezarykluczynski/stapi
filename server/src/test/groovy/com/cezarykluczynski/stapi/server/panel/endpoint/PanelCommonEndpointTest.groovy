package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.dto.UserDTO
import com.cezarykluczynski.stapi.server.panel.service.PanelCurrentUserProvider
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO
import spock.lang.Specification

class PanelCommonEndpointTest extends Specification {

	private PanelCurrentUserProvider panelCurrentUserProviderMock

	private FeatureSwitchApi featureSwitchApiMock

	private PanelCommonEndpoint panelCommonEndpoint

	void setup() {
		panelCurrentUserProviderMock = Mock()
		featureSwitchApiMock = Mock()
		panelCommonEndpoint = new PanelCommonEndpoint(panelCurrentUserProviderMock, featureSwitchApiMock)
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

	void "gets feature switches"() {
		given:
		FeatureSwitchesDTO featureSwitchesDTO = Mock()

		when:
		FeatureSwitchesDTO featureSwitchesDTOOutput = panelCommonEndpoint.featureSwitches()

		then:
		1 * featureSwitchApiMock.all >> featureSwitchesDTO
		0 * _
		featureSwitchesDTOOutput == featureSwitchesDTO
	}

}

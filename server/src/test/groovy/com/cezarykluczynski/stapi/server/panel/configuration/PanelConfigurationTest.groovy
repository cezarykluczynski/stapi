package com.cezarykluczynski.stapi.server.panel.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelAccountSettingsEndpoint
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelAdminEndpoint
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelApiKeysEndpoint
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelCommonEndpoint
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class PanelConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private PanelConfiguration panelConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		panelConfiguration = new PanelConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "PanelAccountSettingsEndpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = panelConfiguration.panelAccountSettingsRestEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PanelAccountSettingsEndpoint, PanelAccountSettingsEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "PanelAdminEndpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = panelConfiguration.panelAdminRestEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PanelAdminEndpoint, PanelAdminEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "PanelApiKeysEndpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = panelConfiguration.panelApiKeysRestEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PanelApiKeysEndpoint, PanelApiKeysEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "PanelCommonEndpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = panelConfiguration.panelCommonRestEndpoint()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PanelCommonEndpoint, PanelCommonEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

}

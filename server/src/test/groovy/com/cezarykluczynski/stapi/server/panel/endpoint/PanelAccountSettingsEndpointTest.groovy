package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.operation.AccountOwnOperationsService
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import spock.lang.Specification

class PanelAccountSettingsEndpointTest extends Specification {

	private AccountOwnOperationsService accountOwnOperationsServiceMock

	private PanelAccountSettingsEndpoint panelAccountSettingsEndpoint

	void setup() {
		accountOwnOperationsServiceMock = Mock()
		panelAccountSettingsEndpoint = new PanelAccountSettingsEndpoint(accountOwnOperationsServiceMock)
	}

	void "removes account"() {
		given:
		AccountRemovalResponseDTO accountRemovalResponseDTO = Mock()

		when:
		AccountRemovalResponseDTO accountRemovalResponseDTOOutput = panelAccountSettingsEndpoint.remove()

		then:
		1 * accountOwnOperationsServiceMock.remove() >> accountRemovalResponseDTO
		0 * _
		accountRemovalResponseDTOOutput == accountRemovalResponseDTO
	}

}

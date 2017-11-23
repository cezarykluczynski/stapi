package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.operation.AccountOwnOperationsService
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import spock.lang.Specification

class PanelAccountSettingsEndpointTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

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

	void "updates basic data"() {
		given:
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = panelAccountSettingsEndpoint.updateBasicData(NAME, EMAIL)

		then:
		1 * accountOwnOperationsServiceMock.updateBasicData(NAME, EMAIL) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

}

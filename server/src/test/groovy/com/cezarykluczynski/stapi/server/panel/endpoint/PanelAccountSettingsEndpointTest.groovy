package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.api.ConsentApi
import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO
import com.cezarykluczynski.stapi.auth.account.operation.AccountOwnOperationsService
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import spock.lang.Specification

class PanelAccountSettingsEndpointTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

	private AccountOwnOperationsService accountOwnOperationsServiceMock

	private ConsentApi consentApiMock

	private PanelAccountSettingsEndpoint panelAccountSettingsEndpoint

	void setup() {
		accountOwnOperationsServiceMock = Mock()
		consentApiMock = Mock()
		panelAccountSettingsEndpoint = new PanelAccountSettingsEndpoint(accountOwnOperationsServiceMock, consentApiMock)
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

	void "reads own consents"() {
		given:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTO = Mock()

		when:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTOOutput = panelAccountSettingsEndpoint.ownConsents()

		then:
		1 * accountOwnOperationsServiceMock.readConsents() >> accountConsentsReadResponseDTO
		0 * _
		accountConsentsReadResponseDTOOutput == accountConsentsReadResponseDTO
	}

	void "updates own consents"() {
		given:
		String[] consentTypes = new String[1]
		consentTypes[0] = ConsentType.TECHNICAL_MAILING.name()
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = panelAccountSettingsEndpoint.updateConsents(consentTypes)

		then:
		1 * accountOwnOperationsServiceMock.updateConsents(consentTypes) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "reads all consents"() {
		given:
		List<ConsentDTO> consentDTOS = Mock()

		when:
		List<ConsentDTO> consentDTOSOutput = panelAccountSettingsEndpoint.consents()

		then:
		1 * consentApiMock.provideAll() >> consentDTOS
		0 * _
		consentDTOSOutput == consentDTOS
	}

}

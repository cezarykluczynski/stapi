package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Sets
import spock.lang.Specification

class AccountOwnOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

	private OAuthSessionHolder oAuthSessionHolderMock

	private AccountOperationsService accountOperationsServiceMock

	AccountConsentTypesExtractor accountConsentTypesExtractorMock

	private AccountOwnOperationsService accountOwnOperationsService

	void setup() {
		oAuthSessionHolderMock = Mock()
		accountOperationsServiceMock = Mock()
		accountConsentTypesExtractorMock = Mock()
		accountOwnOperationsService = new AccountOwnOperationsService(oAuthSessionHolderMock, accountOperationsServiceMock,
				accountConsentTypesExtractorMock)
	}

	void "removes own account"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		AccountRemovalResponseDTO accountRemovalResponseDTO = Mock()

		when:
		AccountRemovalResponseDTO accountRemovalResponseDTOOutput = accountOwnOperationsService.remove()

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * accountOperationsServiceMock.remove(ACCOUNT_ID) >> accountRemovalResponseDTO
		0 * _
		accountRemovalResponseDTOOutput == accountRemovalResponseDTO
	}

	void "updates own account basic data"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = accountOwnOperationsService.updateBasicData(NAME, EMAIL)

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * accountOperationsServiceMock.updateBasicData(ACCOUNT_ID, _ as BasicDataDTO) >> { Long accountId, BasicDataDTO basicDataDTO ->
			assert accountId == ACCOUNT_ID
			assert basicDataDTO.name == NAME
			assert basicDataDTO.email == EMAIL
			accountEditResponseDTO
		}
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "updates own account consents"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		AccountEditResponseDTO accountEditResponseDTO = Mock()
		String[] consentTypesString = new String[1]
		consentTypesString[0] = RandomUtil.randomEnumValue(ConsentType).toString()
		Set<ConsentType> consentTypes = Sets.newHashSet(RandomUtil.randomEnumValue(ConsentType))

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = accountOwnOperationsService.updateConsents(consentTypesString)

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * accountConsentTypesExtractorMock.extract(consentTypesString) >> consentTypes
		1 * accountOperationsServiceMock.updateConsents(ACCOUNT_ID, consentTypes) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "reads own account consents"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTO = Mock()

		when:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTOOutput = accountOwnOperationsService.readConsents()

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * accountOperationsServiceMock.readConsents(ACCOUNT_ID) >> accountConsentsReadResponseDTO
		0 * _
		accountConsentsReadResponseDTOOutput == accountConsentsReadResponseDTO
	}

}

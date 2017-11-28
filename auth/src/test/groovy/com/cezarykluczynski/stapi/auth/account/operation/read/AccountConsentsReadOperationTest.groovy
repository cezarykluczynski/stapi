package com.cezarykluczynski.stapi.auth.account.operation.read

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.google.common.collect.Sets
import spock.lang.Specification

class AccountConsentsReadOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final String CONSENT_CODE = 'CONSENT_CODE'

	private AccountApi accountApiMock

	private AccountConsentsReadResponseDTOFactory accountConsentsReadResponseDTOFactoryMock

	private AccountConsentTypesExtractor accountConsentTypesExtractorMock

	private AccountConsentsReadOperation accountConsentsReadOperation

	void setup() {
		accountApiMock = Mock()
		accountConsentsReadResponseDTOFactoryMock = Mock()
		accountConsentTypesExtractorMock = Mock()
		accountConsentsReadOperation = new AccountConsentsReadOperation(accountApiMock, accountConsentsReadResponseDTOFactoryMock,
				accountConsentTypesExtractorMock)
	}

	void "when account could not be found, unsuccessful response is returned"() {
		given:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTO = Mock()

		when:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTOOutput = accountConsentsReadOperation.execute(ACCOUNT_ID)

		then:
		1 * accountApiMock.findOneWithConsents(ACCOUNT_ID) >> Optional.empty()
		1 * accountConsentsReadResponseDTOFactoryMock.createUnsuccessful() >> accountConsentsReadResponseDTO
		0 * _
		accountConsentsReadResponseDTOOutput == accountConsentsReadResponseDTO
	}

	void "when account is found, consents are extracted and returned"() {
		given:
		Account account = Mock()
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTO = Mock()

		when:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTOOutput = accountConsentsReadOperation.execute(ACCOUNT_ID)

		then:
		1 * accountApiMock.findOneWithConsents(ACCOUNT_ID) >> Optional.of(account)
		1 * accountConsentTypesExtractorMock.extractAsStrings(account) >> Sets.newHashSet(CONSENT_CODE)
		1 * accountConsentsReadResponseDTOFactoryMock.createSuccessful(Sets.newHashSet(CONSENT_CODE)) >> accountConsentsReadResponseDTO
		0 * _
		accountConsentsReadResponseDTOOutput == accountConsentsReadResponseDTO
	}

}

package com.cezarykluczynski.stapi.auth.account.operation.edit

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Sets
import spock.lang.Specification

class ConsentsEditOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountApi accountApiMock

	private AccountConsentTypesExtractor accountConsentTypesExtractorMock

	private AccountEditResponseDTOFactory accountEditResponseDTOFactoryMock

	private ConsentsEditOperation consentsEditOperation

	void setup() {
		accountApiMock = Mock()
		accountConsentTypesExtractorMock = Mock()
		accountEditResponseDTOFactoryMock = Mock()
		consentsEditOperation = new ConsentsEditOperation(accountApiMock, accountConsentTypesExtractorMock, accountEditResponseDTOFactoryMock)
	}

	void "throws exception when account ID is null"() {
		when:
		consentsEditOperation.execute(null, Sets.newHashSet())

		then:
		thrown(NullPointerException)
	}

	void "throws exception when consents set is null"() {
		when:
		consentsEditOperation.execute(ACCOUNT_ID, null)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when account could not be found"() {
		when:
		consentsEditOperation.execute(ACCOUNT_ID, Sets.newHashSet())

		then:
		1 * accountApiMock.findByIdWithConsents(ACCOUNT_ID) >> Optional.empty()
		0 * _
		thrown(StapiRuntimeException)
	}

	void "when current consents equal new consents, unchanged response is returned"() {
		given:
		Account account = Mock()
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = consentsEditOperation
				.execute(ACCOUNT_ID, Sets.newHashSet(ConsentType.TECHNICAL_MAILING))

		then:
		1 * accountApiMock.findByIdWithConsents(ACCOUNT_ID) >> Optional.of(account)
		1 * accountConsentTypesExtractorMock.extract(account) >> Sets.newHashSet(ConsentType.TECHNICAL_MAILING)
		1 * accountEditResponseDTOFactoryMock.createUnchanged() >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "when account can be saved, successful response is returned"() {
		given:
		Account account = Mock()
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = consentsEditOperation
				.execute(ACCOUNT_ID, Sets.newHashSet(ConsentType.TECHNICAL_MAILING))

		then:
		1 * accountApiMock.findByIdWithConsents(ACCOUNT_ID) >> Optional.of(account)
		1 * accountConsentTypesExtractorMock.extract(account) >> Sets.newHashSet()
		1 * accountApiMock.updateConsents(ACCOUNT_ID, Sets.newHashSet(ConsentType.TECHNICAL_MAILING))
		1 * accountEditResponseDTOFactoryMock.createSuccessful() >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "when account cannot be saved, unsuccessful response is returned"() {
		given:
		Account account = Mock()
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = consentsEditOperation
				.execute(ACCOUNT_ID, Sets.newHashSet(ConsentType.TECHNICAL_MAILING))

		then:
		1 * accountApiMock.findByIdWithConsents(ACCOUNT_ID) >> Optional.of(account)
		1 * accountConsentTypesExtractorMock.extract(account) >> Sets.newHashSet()
		1 * accountApiMock.updateConsents(ACCOUNT_ID, Sets.newHashSet(ConsentType.TECHNICAL_MAILING)) >> {
			throw new StapiRuntimeException('')
		}
		1 * accountEditResponseDTOFactoryMock.createUnsuccessful(AccountEditResponseDTO.FailReason.CONSENTS_CANNOT_BE_SAVED) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

}

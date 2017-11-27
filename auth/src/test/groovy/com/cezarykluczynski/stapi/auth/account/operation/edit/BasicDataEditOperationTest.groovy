package com.cezarykluczynski.stapi.auth.account.operation.edit

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class BasicDataEditOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final String NAME_1 = 'NAME_1'
	private static final String NAME_2 = 'NAME_2'
	private static final String EMAIL_1 = 'EMAIL_1'
	private static final String EMAIL_2 = 'EMAIL_2'

	private AccountApi accountApiMock

	private BasicDataNameSpecification basicDataNameSpecificationMock

	private BasicDataEmailSpecification basicDataEmailSpecificationMock

	private AccountEditResponseDTOFactory accountEditResponseDTOFactoryMock

	private BasicDataEditOperation basicDataEditOperation

	void setup() {
		accountApiMock = Mock()
		basicDataNameSpecificationMock = Mock()
		basicDataEmailSpecificationMock = Mock()
		accountEditResponseDTOFactoryMock = Mock()
		basicDataEditOperation = new BasicDataEditOperation(accountApiMock, basicDataNameSpecificationMock, basicDataEmailSpecificationMock,
				accountEditResponseDTOFactoryMock)
	}

	void "when account ID is null, exception is thrown"() {
		given:
		BasicDataDTO basicDataDTO = Mock()

		when:
		basicDataEditOperation.execute(null, basicDataDTO)

		then:
		thrown(NullPointerException)
	}

	void "when BasicDataDTO is null, exception is thrown"() {
		when:
		basicDataEditOperation.execute(ACCOUNT_ID, null)

		then:
		thrown(NullPointerException)
	}

	void "when account is not found, exception is thrown"() {
		given:
		BasicDataDTO basicDataDTO = Mock()

		when:
		basicDataEditOperation.execute(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountApiMock.findById(ACCOUNT_ID) >> Optional.empty()
		thrown(StapiRuntimeException)
	}

	void "is name and email was not changed, unchanged response is returned"() {
		given:
		BasicDataDTO basicDataDTO = BasicDataDTO.builder()
				.name(NAME_1)
				.email(EMAIL_1)
				.build()
		Account account = new Account(
				name: NAME_1,
				email: EMAIL_1)
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = basicDataEditOperation.execute(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountApiMock.findById(ACCOUNT_ID) >> Optional.of(account)
		1 * accountEditResponseDTOFactoryMock.createUnchanged() >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "when name changed and is not valid, unsuccessful response is returned"() {
		given:
		BasicDataDTO basicDataDTO = BasicDataDTO.builder()
				.name(NAME_2)
				.email(EMAIL_1)
				.build()
		Account account = new Account(
				name: NAME_1,
				email: EMAIL_1)
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = basicDataEditOperation.execute(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountApiMock.findById(ACCOUNT_ID) >> Optional.of(account)
		1 * basicDataNameSpecificationMock.isSatisfiedBy(NAME_2) >> false
		1 * accountEditResponseDTOFactoryMock.createUnsuccessful(AccountEditResponseDTO.FailReason.INVALID_NAME) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "when email changed and is not valid, unsuccessful response is returned"() {
		given:
		BasicDataDTO basicDataDTO = BasicDataDTO.builder()
				.name(NAME_1)
				.email(EMAIL_2)
				.build()
		Account account = new Account(
				name: NAME_1,
				email: EMAIL_1)
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = basicDataEditOperation.execute(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountApiMock.findById(ACCOUNT_ID) >> Optional.of(account)
		1 * basicDataEmailSpecificationMock.isSatisfiedBy(EMAIL_2) >> false
		1 * accountEditResponseDTOFactoryMock.createUnsuccessful(AccountEditResponseDTO.FailReason.INVALID_EMAIL) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "when email and name changed and are valid, both are set to account"() {
		given:
		BasicDataDTO basicDataDTO = BasicDataDTO.builder()
				.name(NAME_2)
				.email(EMAIL_2)
				.build()
		Account account = new Account(
				name: NAME_1,
				email: EMAIL_1)
		AccountEditResponseDTO accountEditResponseDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = basicDataEditOperation.execute(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountApiMock.findById(ACCOUNT_ID) >> Optional.of(account)
		1 * basicDataNameSpecificationMock.isSatisfiedBy(NAME_2) >> true
		1 * basicDataEmailSpecificationMock.isSatisfiedBy(EMAIL_2) >> true
		1 * accountEditResponseDTOFactoryMock.createSuccessful() >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
		account.name == NAME_2
		account.email == EMAIL_2
	}

}

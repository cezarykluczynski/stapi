package com.cezarykluczynski.stapi.auth.account.operation.edit

import spock.lang.Specification

class AccountEditResponseDTOFactoryTest extends Specification {

	private AccountEditResponseDTOFactory accountEditResponseDTOFactory

	void setup() {
		accountEditResponseDTOFactory = new AccountEditResponseDTOFactory()
	}

	void "creates unsuccessful response with reason"() {
		when:
		AccountEditResponseDTO accountEditResponseDTO = accountEditResponseDTOFactory
				.createUnsuccessful(AccountEditResponseDTO.FailReason.INVALID_EMAIL)

		then:
		!accountEditResponseDTO.successful
		!accountEditResponseDTO.changed
		accountEditResponseDTO.failReason == AccountEditResponseDTO.FailReason.INVALID_EMAIL
	}

	void "creates unchanged response"() {
		when:
		AccountEditResponseDTO accountEditResponseDTO = accountEditResponseDTOFactory.createUnchanged()

		then:
		accountEditResponseDTO.successful
		!accountEditResponseDTO.changed
		accountEditResponseDTO.failReason == null
	}

	void "creates successful response"() {
		when:
		AccountEditResponseDTO accountEditResponseDTO = accountEditResponseDTOFactory.createSuccessful()

		then:
		accountEditResponseDTO.successful
		accountEditResponseDTO.changed
		accountEditResponseDTO.failReason == null
	}

}

package com.cezarykluczynski.stapi.auth.account.operation.removal

import spock.lang.Specification

class AccountRemovalResponseDTOFactoryTest extends Specification {

	private AccountRemovalResponseDTOFactory accountRemovalResponseDTOFactory

	void setup() {
		accountRemovalResponseDTOFactory = new AccountRemovalResponseDTOFactory()
	}

	void "creates successful response"() {
		when:
		AccountRemovalResponseDTO accountRemovalResponseDTO = accountRemovalResponseDTOFactory.createSuccessful()

		then:
		accountRemovalResponseDTO.successful
	}

	void "creates unsuccessful response"() {
		when:
		AccountRemovalResponseDTO accountRemovalResponseDTO = accountRemovalResponseDTOFactory.createUnsuccessful()

		then:
		!accountRemovalResponseDTO.successful
	}

}

package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import spock.lang.Specification

class AccountOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountRemovalOperation accountRemovalOperationMock

	private AccountOperationsService accountOperationsService

	void setup() {
		accountRemovalOperationMock = Mock()
		accountOperationsService = new AccountOperationsService(accountRemovalOperationMock)
	}

	void "removes account"() {
		given:
		AccountRemovalResponseDTO accountRemovalResponseDTO = Mock()

		when:
		AccountRemovalResponseDTO accountRemovalResponseDTOOutput = accountOperationsService.remove(ACCOUNT_ID)

		then:
		1 * accountRemovalOperationMock.execute(ACCOUNT_ID) >> accountRemovalResponseDTO
		0 * _
		accountRemovalResponseDTOOutput == accountRemovalResponseDTO
	}

}

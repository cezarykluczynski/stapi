package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditOperation
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import spock.lang.Specification

class AccountOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountRemovalOperation accountRemovalOperationMock

	private AccountEditOperation accountEditOperationMock

	private AccountOperationsService accountOperationsService

	void setup() {
		accountRemovalOperationMock = Mock()
		accountEditOperationMock = Mock()
		accountOperationsService = new AccountOperationsService(accountRemovalOperationMock, accountEditOperationMock)
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

	void "updates account basic data"() {
		given:
		AccountEditResponseDTO accountEditResponseDTO = Mock()
		BasicDataDTO basicDataDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = accountOperationsService.updateBasicData(ACCOUNT_ID, basicDataDTO)

		then:
		1 * accountEditOperationMock.execute(ACCOUNT_ID, basicDataDTO) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

}

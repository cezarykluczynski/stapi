package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO
import spock.lang.Specification

class AccountAdminOperationsServiceTest extends Specification {

	private AccountOperationsService accountOperationsServiceMock

	private AccountAdminOperationsService accountAdminOperationsService

	void setup() {
		accountOperationsServiceMock = Mock()
		accountAdminOperationsService = new AccountAdminOperationsService(accountOperationsServiceMock)
	}

	void "searches for accounts"() {
		given:
		AccountsSearchCriteriaDTO accountsSearchCriteriaDTO = Mock()
		AccountReadResponseDTO accountReadResponseDTO = Mock()

		when:
		AccountReadResponseDTO accountReadResponseDTOOutput = accountAdminOperationsService.search(accountsSearchCriteriaDTO)

		then:
		1 * accountOperationsServiceMock.search(accountsSearchCriteriaDTO) >> accountReadResponseDTO
		0 * _
		accountReadResponseDTOOutput == accountReadResponseDTO
	}

}

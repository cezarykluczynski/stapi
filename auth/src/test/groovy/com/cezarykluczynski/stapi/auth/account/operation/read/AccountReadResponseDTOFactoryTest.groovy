package com.cezarykluczynski.stapi.auth.account.operation.read

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO
import com.cezarykluczynski.stapi.util.wrapper.Pager
import spock.lang.Specification

class AccountReadResponseDTOFactoryTest extends Specification {

	private AccountReadResponseDTOFactory accountReadResponseDTOFactory

	void setup() {
		accountReadResponseDTOFactory = new AccountReadResponseDTOFactory()
	}

	void "creates response with accounts and pager"() {
		given:
		List<AccountDTO> accounts = Mock()
		Pager pager = Mock()

		when:
		AccountReadResponseDTO accountReadResponseDTO = accountReadResponseDTOFactory.createWithAccountsAndPager(accounts, pager)

		then:
		accountReadResponseDTO.successful
		accountReadResponseDTO.accounts == accounts
		accountReadResponseDTO.pager == pager
	}

}

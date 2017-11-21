package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import spock.lang.Specification

class AccountOwnOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private OAuthSessionHolder oAuthSessionHolderMock

	private AccountOperationsService accountOperationsServiceMock

	private AccountOwnOperationsService accountOwnOperationsService

	void setup() {
		oAuthSessionHolderMock = Mock()
		accountOperationsServiceMock = Mock()
		accountOwnOperationsService = new AccountOwnOperationsService(oAuthSessionHolderMock, accountOperationsServiceMock)
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

}

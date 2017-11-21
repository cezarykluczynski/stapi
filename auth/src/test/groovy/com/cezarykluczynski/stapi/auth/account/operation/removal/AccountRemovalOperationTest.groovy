package com.cezarykluczynski.stapi.auth.account.operation.removal

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import org.hibernate.HibernateException
import spock.lang.Specification

class AccountRemovalOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountApi accountApiMock

	private AccountRemovalResponseDTOFactory accountRemovalResponseDTOFactoryMock

	private AccountRemovalOperation accountRemovalOperation

	void setup() {
		accountApiMock = Mock()
		accountRemovalResponseDTOFactoryMock = Mock()
		accountRemovalOperation = new AccountRemovalOperation(accountApiMock, accountRemovalResponseDTOFactoryMock)
	}

	void "when account is removed, successful response is returned"() {
		given:
		AccountRemovalResponseDTO accountRemovalResponseDTO = Mock()

		when:
		accountRemovalOperation.execute(ACCOUNT_ID)

		then:
		1 * accountApiMock.remove(ACCOUNT_ID)
		1 * accountRemovalResponseDTOFactoryMock.createSuccessful() >> accountRemovalResponseDTO
		0 * _
	}

	void "when account cannot be removed, unsuccessful response is returned"() {
		given:
		AccountRemovalResponseDTO accountRemovalResponseDTO = Mock()

		when:
		accountRemovalOperation.execute(ACCOUNT_ID)

		then:
		1 * accountApiMock.remove(ACCOUNT_ID) >> { Long account ->
			throw new HibernateException('')
		}
		1 * accountRemovalResponseDTOFactoryMock.createUnsuccessful() >> accountRemovalResponseDTO
		0 * _
	}

}

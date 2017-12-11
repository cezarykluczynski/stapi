package com.cezarykluczynski.stapi.auth.account.operation

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO
import com.cezarykluczynski.stapi.auth.account.operation.edit.BasicDataEditOperation
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.edit.ConsentsEditOperation
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadOperation
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsReadPageOperation
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Sets
import spock.lang.Specification

class AccountOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountRemovalOperation accountRemovalOperationMock

	private AccountsReadPageOperation apiKeysReadPageOperationMock

	private BasicDataEditOperation basicDataEditOperationMock

	private ConsentsEditOperation consentsEditOperationMock

	private AccountConsentsReadOperation accountConsentsReadOperationMock

	private AccountOperationsService accountOperationsService

	void setup() {
		accountRemovalOperationMock = Mock()
		apiKeysReadPageOperationMock = Mock()
		basicDataEditOperationMock = Mock()
		consentsEditOperationMock = Mock()
		accountConsentsReadOperationMock = Mock()
		accountOperationsService = new AccountOperationsService(accountRemovalOperationMock, apiKeysReadPageOperationMock, basicDataEditOperationMock,
				consentsEditOperationMock, accountConsentsReadOperationMock)
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

	void "searches for accounts"() {
		given:
		AccountsSearchCriteriaDTO accountsSearchCriteriaDTO = Mock()
		AccountReadResponseDTO accountReadResponseDTO = Mock()

		when:
		AccountReadResponseDTO accountReadResponseDTOOutput = accountOperationsService.search(accountsSearchCriteriaDTO)

		then:
		1 * apiKeysReadPageOperationMock.execute(accountsSearchCriteriaDTO) >> accountReadResponseDTO
		0 * _
		accountReadResponseDTOOutput == accountReadResponseDTO
	}

	void "updates account basic data"() {
		given:
		AccountEditResponseDTO accountEditResponseDTO = Mock()
		BasicDataDTO basicDataDTO = Mock()

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = accountOperationsService.updateBasicData(ACCOUNT_ID, basicDataDTO)

		then:
		1 * basicDataEditOperationMock.execute(ACCOUNT_ID, basicDataDTO) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "updates account consents"() {
		given:
		AccountEditResponseDTO accountEditResponseDTO = Mock()
		Set<ConsentType> consentTypes = Sets.newHashSet(RandomUtil.randomEnumValue(ConsentType))

		when:
		AccountEditResponseDTO accountEditResponseDTOOutput = accountOperationsService.updateConsents(ACCOUNT_ID, consentTypes)

		then:
		1 * consentsEditOperationMock.execute(ACCOUNT_ID, consentTypes) >> accountEditResponseDTO
		0 * _
		accountEditResponseDTOOutput == accountEditResponseDTO
	}

	void "reads account consents"() {
		given:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTO = Mock()

		when:
		AccountConsentsReadResponseDTO accountConsentsReadResponseDTOOutput = accountOperationsService.readConsents(ACCOUNT_ID)

		then:
		1 * accountConsentsReadOperationMock.execute(ACCOUNT_ID) >> accountConsentsReadResponseDTO
		0 * _
		accountConsentsReadResponseDTOOutput == accountConsentsReadResponseDTO
	}

}

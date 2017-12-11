package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.BasicDataEditOperation;
import com.cezarykluczynski.stapi.auth.account.operation.edit.ConsentsEditOperation;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadOperation;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountConsentsReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsReadPageOperation;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class AccountOperationsService {

	private final AccountRemovalOperation accountRemovalOperation;

	private final AccountsReadPageOperation apiKeysReadPageOperation;

	private final BasicDataEditOperation basicDataEditOperation;

	private final ConsentsEditOperation consentsEditOperation;

	private final AccountConsentsReadOperation accountConsentsReadOperation;

	AccountOperationsService(AccountRemovalOperation accountRemovalOperation, AccountsReadPageOperation apiKeysReadPageOperation,
			BasicDataEditOperation basicDataEditOperation, ConsentsEditOperation consentsEditOperation,
			AccountConsentsReadOperation accountConsentsReadOperation) {
		this.accountRemovalOperation = accountRemovalOperation;
		this.apiKeysReadPageOperation = apiKeysReadPageOperation;
		this.basicDataEditOperation = basicDataEditOperation;
		this.consentsEditOperation = consentsEditOperation;
		this.accountConsentsReadOperation = accountConsentsReadOperation;
	}

	AccountRemovalResponseDTO remove(Long accountId) {
		return accountRemovalOperation.execute(accountId);
	}

	AccountReadResponseDTO search(AccountsSearchCriteriaDTO accountsSearchCriteriaDTO) {
		return apiKeysReadPageOperation.execute(accountsSearchCriteriaDTO);
	}

	AccountEditResponseDTO updateBasicData(Long accountId, BasicDataDTO basicDataDTO) {
		return basicDataEditOperation.execute(accountId, basicDataDTO);
	}

	AccountEditResponseDTO updateConsents(Long accountId, Set<ConsentType> consentTypes) {
		return consentsEditOperation.execute(accountId, consentTypes);
	}

	AccountConsentsReadResponseDTO readConsents(Long accountId) {
		return accountConsentsReadOperation.execute(accountId);
	}

}

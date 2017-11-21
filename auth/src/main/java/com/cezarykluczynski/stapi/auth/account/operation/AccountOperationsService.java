package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import org.springframework.stereotype.Service;

@Service
class AccountOperationsService {

	private final AccountRemovalOperation accountRemovalOperation;

	AccountOperationsService(AccountRemovalOperation accountRemovalOperation) {
		this.accountRemovalOperation = accountRemovalOperation;
	}

	AccountRemovalResponseDTO remove(Long accountId) {
		return accountRemovalOperation.execute(accountId);
	}

}

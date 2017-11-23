package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditOperation;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import org.springframework.stereotype.Service;

@Service
class AccountOperationsService {

	private final AccountRemovalOperation accountRemovalOperation;

	private final AccountEditOperation accountEditOperation;

	AccountOperationsService(AccountRemovalOperation accountRemovalOperation, AccountEditOperation accountEditOperation) {
		this.accountRemovalOperation = accountRemovalOperation;
		this.accountEditOperation = accountEditOperation;
	}

	AccountRemovalResponseDTO remove(Long accountId) {
		return accountRemovalOperation.execute(accountId);
	}

	AccountEditResponseDTO updateBasicData(Long accountId, BasicDataDTO basicDataDTO) {
		return accountEditOperation.execute(accountId, basicDataDTO);
	}
}

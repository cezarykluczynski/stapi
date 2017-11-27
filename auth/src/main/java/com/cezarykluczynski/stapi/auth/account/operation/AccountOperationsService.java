package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.dto.BasicDataDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountEditResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.edit.BasicDataEditOperation;
import com.cezarykluczynski.stapi.auth.account.operation.edit.ConsentsEditOperation;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalOperation;
import com.cezarykluczynski.stapi.auth.account.operation.removal.AccountRemovalResponseDTO;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class AccountOperationsService {

	private final AccountRemovalOperation accountRemovalOperation;

	private final BasicDataEditOperation basicDataEditOperation;

	private final ConsentsEditOperation consentsEditOperation;

	AccountOperationsService(AccountRemovalOperation accountRemovalOperation, BasicDataEditOperation basicDataEditOperation,
			ConsentsEditOperation consentsEditOperation) {
		this.accountRemovalOperation = accountRemovalOperation;
		this.basicDataEditOperation = basicDataEditOperation;
		this.consentsEditOperation = consentsEditOperation;
	}

	AccountRemovalResponseDTO remove(Long accountId) {
		return accountRemovalOperation.execute(accountId);
	}

	AccountEditResponseDTO updateBasicData(Long accountId, BasicDataDTO basicDataDTO) {
		return basicDataEditOperation.execute(accountId, basicDataDTO);
	}

	AccountEditResponseDTO updateConsents(Long accountId, Set<ConsentType> consentTypes) {
		return consentsEditOperation.execute(accountId, consentTypes);
	}
}

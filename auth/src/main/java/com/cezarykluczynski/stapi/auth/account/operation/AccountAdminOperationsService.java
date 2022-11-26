package com.cezarykluczynski.stapi.auth.account.operation;

import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO;
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class AccountAdminOperationsService {

	private final AccountOperationsService accountOperationsService;

	public AccountAdminOperationsService(AccountOperationsService accountOperationsService) {
		this.accountOperationsService = accountOperationsService;
	}

	public AccountReadResponseDTO search(AccountsSearchCriteriaDTO accountsSearchCriteriaDTO) {
		return accountOperationsService.search(accountsSearchCriteriaDTO);
	}

}

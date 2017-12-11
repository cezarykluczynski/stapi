package com.cezarykluczynski.stapi.auth.account.mapper;

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

	public AccountDTO map(Account account) {
		if (account == null) {
			throw new StapiRuntimeException("No account entity to map from");
		}

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setId(account.getId());
		accountDTO.setGitHubAccountId(account.getGitHubUserId());
		accountDTO.setName(account.getName());
		accountDTO.setEmail(account.getEmail());
		return accountDTO;
	}

}

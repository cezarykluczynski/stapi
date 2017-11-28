package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountConsentsReadOperation {

	private final AccountApi accountApi;

	private final AccountConsentsReadResponseDTOFactory accountConsentsReadResponseDTOFactory;

	private final AccountConsentTypesExtractor accountConsentTypesExtractor;

	public AccountConsentsReadOperation(AccountApi accountApi, AccountConsentsReadResponseDTOFactory accountConsentsReadResponseDTOFactory,
			AccountConsentTypesExtractor accountConsentTypesExtractor) {
		this.accountApi = accountApi;
		this.accountConsentsReadResponseDTOFactory = accountConsentsReadResponseDTOFactory;
		this.accountConsentTypesExtractor = accountConsentTypesExtractor;
	}

	public AccountConsentsReadResponseDTO execute(Long accountId) {
		Optional<Account> accountOptional = accountApi.findOneWithConsents(accountId);
		if (!accountOptional.isPresent()) {
			return accountConsentsReadResponseDTOFactory.createUnsuccessful();
		} else {
			return accountConsentsReadResponseDTOFactory.createSuccessful(accountConsentTypesExtractor.extractAsStrings(accountOptional.get()));
		}
	}

}

package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.auth.account.api.AccountApi;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConsentsEditOperation {

	private final AccountApi accountApi;

	private final AccountConsentTypesExtractor accountConsentTypesExtractor;

	private final AccountEditResponseDTOFactory accountEditResponseDTOFactory;

	public ConsentsEditOperation(AccountApi accountApi, AccountConsentTypesExtractor accountConsentTypesExtractor,
			AccountEditResponseDTOFactory accountEditResponseDTOFactory) {
		this.accountApi = accountApi;
		this.accountConsentTypesExtractor = accountConsentTypesExtractor;
		this.accountEditResponseDTOFactory = accountEditResponseDTOFactory;
	}

	public AccountEditResponseDTO execute(Long accountId, Set<ConsentType> consentTypes) {
		Preconditions.checkNotNull(accountId, "Account ID cannot be null");
		Preconditions.checkNotNull(consentTypes, "Consents cannot be null");
		Account account = accountApi.findByIdWithConsents(accountId).orElseThrow(() -> new StapiRuntimeException("Account not found!"));

		if (accountConsentTypesExtractor.extract(account).equals(consentTypes)) {
			return accountEditResponseDTOFactory.createUnchanged();
		}

		return saveAndGetResult(accountId, consentTypes);
	}

	private AccountEditResponseDTO saveAndGetResult(Long accountId, Set<ConsentType> consentTypes) {
		try {
			accountApi.updateConsents(accountId, consentTypes);
			return accountEditResponseDTOFactory.createSuccessful();
		} catch (Exception e) {
			return accountEditResponseDTOFactory.createUnsuccessful(AccountEditResponseDTO.FailReason.CONSENTS_CANNOT_BE_SAVED);
		}
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

@Service
class ApiKeyCreationValidator {

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyLimitProvider apiKeyLimitProvider;

	ApiKeyCreationValidator(ApiKeyRepository apiKeyRepository, ApiKeyLimitProvider apiKeyLimitProvider) {
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyLimitProvider = apiKeyLimitProvider;
	}

	boolean canBeCreated(Long accountId) {
		return apiKeyRepository.countByAccountId(accountId) <= apiKeyLimitProvider.provideKeyLimitPerAccount() - 1;
	}

	boolean haveAllowedNumberOfKeys(Long accountId) {
		return apiKeyRepository.countByAccountId(accountId) <= apiKeyLimitProvider.provideKeyLimitPerAccount();
	}

}

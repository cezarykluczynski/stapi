package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

@Service
class ApiKeyBlockRelatedValidator {

	private final ApiKeyRepository apiKeyRepository;

	ApiKeyBlockRelatedValidator(ApiKeyRepository apiKeyRepository) {
		this.apiKeyRepository = apiKeyRepository;
	}

	void validate(Long accountId, Long apiKeyId) {
		ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);

		if (apiKey == null) {
			throw new KeyDoesNotExistException();
		}

		if (Long.compare(apiKey.getAccountId(), accountId) != 0) {
			throw new KeyNotOwnedByAccountException();
		}
	}

}

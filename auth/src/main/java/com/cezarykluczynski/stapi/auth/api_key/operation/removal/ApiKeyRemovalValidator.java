package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class ApiKeyRemovalValidator {

	private final ApiKeyRepository apiKeyRepository;

	ApiKeyRemovalValidator(ApiKeyRepository apiKeyRepository) {
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

		if (Boolean.TRUE.equals(apiKey.getBlocked())) {
			throw new BlockedKeyException();
		}
	}

}

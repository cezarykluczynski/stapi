package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile(SpringProfile.AUTH)
public class ApiKeyDTOFactory {

	private final ApiKeyWithThrottleFactory apiKeyWithThrottleFactory;

	private final ApiKeyWithThrottlePersister apiKeyWithThrottlePersister;

	private final ApiKeyCreationValidator apiKeyCreationValidator;

	public ApiKeyDTOFactory(ApiKeyWithThrottleFactory apiKeyWithThrottleFactory, ApiKeyWithThrottlePersister apiKeyWithThrottlePersister,
			ApiKeyCreationValidator apiKeyCreationValidator) {
		this.apiKeyWithThrottleFactory = apiKeyWithThrottleFactory;
		this.apiKeyWithThrottlePersister = apiKeyWithThrottlePersister;
		this.apiKeyCreationValidator = apiKeyCreationValidator;
	}

	@Transactional(rollbackFor = MultipleApiKeysSimultaneouslyCreatedException.class)
	public ApiKeyDTO create(Long accountId) {
		ApiKeyDTO apiKeyDTO = apiKeyWithThrottlePersister.persistAndGetApiKeyDTO(apiKeyWithThrottleFactory.createForAccount(accountId));

		if (!apiKeyCreationValidator.haveAllowedNumberOfKeys(accountId)) {
			throw new MultipleApiKeysSimultaneouslyCreatedException();
		}

		return apiKeyDTO;
	}

}

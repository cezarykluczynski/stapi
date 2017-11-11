package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyRemovalOperation {

	private final ApiKeyRemovalValidator apiKeyRemovalValidator;

	private final ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapper;

	private final ApiKeyRepository apiKeyRepository;

	private final ThrottleRepository throttleRepository;

	private final ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory;

	public ApiKeyRemovalOperation(ApiKeyRemovalValidator apiKeyRemovalValidator, ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapper,
			ApiKeyRepository apiKeyRepository, ThrottleRepository throttleRepository,
			ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory) {
		this.apiKeyRemovalValidator = apiKeyRemovalValidator;
		this.apiKeyRemovalExceptionMapper = apiKeyRemovalExceptionMapper;
		this.apiKeyRepository = apiKeyRepository;
		this.throttleRepository = throttleRepository;
		this.apiKeyRemovalResponseDTOFactory = apiKeyRemovalResponseDTOFactory;
	}

	@Transactional
	public ApiKeyRemovalResponseDTO execute(Long accountId, Long apiKeyId) {
		try {
			apiKeyRemovalValidator.validate(accountId, apiKeyId);
		} catch (ApiKeyException apiKeyException) {
			return apiKeyRemovalExceptionMapper.map(apiKeyException);
		}

		ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);
		apiKeyRepository.delete(apiKeyId);
		throttleRepository.deleteByApiKey(apiKey.getApiKey());
		return apiKeyRemovalResponseDTOFactory.createSuccessful();
	}

}

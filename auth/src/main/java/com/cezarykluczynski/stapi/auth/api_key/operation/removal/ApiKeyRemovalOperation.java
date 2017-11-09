package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyRemovalOperation {

	private final ApiKeyRemovalValidator apiKeyRemovalValidator;

	private final ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapper;

	private final ApiKeyRepository apiKeyRepository;

	private final ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory;

	public ApiKeyRemovalOperation(ApiKeyRemovalValidator apiKeyRemovalValidator, ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapper,
			ApiKeyRepository apiKeyRepository, ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory) {
		this.apiKeyRemovalValidator = apiKeyRemovalValidator;
		this.apiKeyRemovalExceptionMapper = apiKeyRemovalExceptionMapper;
		this.apiKeyRepository = apiKeyRepository;
		this.apiKeyRemovalResponseDTOFactory = apiKeyRemovalResponseDTOFactory;
	}

	public ApiKeyRemovalResponseDTO execute(Long accountId, Long apiKeyId) {
		try {
			apiKeyRemovalValidator.validate(accountId, apiKeyId);
		} catch (ApiKeyRemovalException apiKeyRemovalException) {
			return apiKeyRemovalExceptionMapper.map(apiKeyRemovalException);
		}

		apiKeyRepository.delete(apiKeyId);
		return apiKeyRemovalResponseDTOFactory.createSuccessful();
	}

}

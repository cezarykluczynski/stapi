package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyBlockRelatedOperation {

	private final ApiKeyBlockRelatedValidator apiKeyBlockRelatedValidator;

	private final ApiKeyBlockRelatedExceptionMapper apiKeyBlockRelatedExceptionMapper;

	private final ApiKeyWithThrottleBlockService apiKeyWithThrottleBlockService;

	private final ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactory;

	public ApiKeyBlockRelatedOperation(ApiKeyBlockRelatedValidator apiKeyBlockRelatedValidator,
			ApiKeyBlockRelatedExceptionMapper apiKeyBlockRelatedExceptionMapper, ApiKeyWithThrottleBlockService apiKeyWithThrottleBlockService,
			ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactory) {
		this.apiKeyBlockRelatedValidator = apiKeyBlockRelatedValidator;
		this.apiKeyBlockRelatedExceptionMapper = apiKeyBlockRelatedExceptionMapper;
		this.apiKeyWithThrottleBlockService = apiKeyWithThrottleBlockService;
		this.apiKeyBlockRelatedResponseDTOFactory = apiKeyBlockRelatedResponseDTOFactory;
	}

	public ApiKeyBlockRelatedResponseDTO execute(Long accountId, Long apiKeyId, boolean blocked) {
		ApiKeyBlockRelatedResponseDTO failedApiKeyBlockRelatedResponseDTO = tryGetFailedResponse(accountId, apiKeyId);
		if (failedApiKeyBlockRelatedResponseDTO != null) {
			return failedApiKeyBlockRelatedResponseDTO;
		}

		apiKeyWithThrottleBlockService.changeBlockStatus(apiKeyId, blocked);
		return apiKeyBlockRelatedResponseDTOFactory.createSuccessful();
	}

	private ApiKeyBlockRelatedResponseDTO tryGetFailedResponse(Long accountId, Long apiKeyId) {
		try {
			apiKeyBlockRelatedValidator.validate(accountId, apiKeyId);
			return null;
		} catch (ApiKeyException apiKeyException) {
			return apiKeyBlockRelatedExceptionMapper.map(apiKeyException);
		}
	}

}

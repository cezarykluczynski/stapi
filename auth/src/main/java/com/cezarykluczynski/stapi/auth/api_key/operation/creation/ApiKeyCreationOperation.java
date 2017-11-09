package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyCreationOperation {

	private final ApiKeyCreationValidator apiKeyCreationValidator;

	private final ApiKeyDTOFactory apiKeyDTOFactory;

	private final ApiKeyCreationResponseDTOFactory apiKeyCreationResponseDTOFactory;

	public ApiKeyCreationOperation(ApiKeyCreationValidator apiKeyCreationValidator, ApiKeyDTOFactory apiKeyDTOFactory,
			ApiKeyCreationResponseDTOFactory apiKeyCreationResponseDTOFactory) {
		this.apiKeyCreationValidator = apiKeyCreationValidator;
		this.apiKeyDTOFactory = apiKeyDTOFactory;
		this.apiKeyCreationResponseDTOFactory = apiKeyCreationResponseDTOFactory;
	}

	public synchronized ApiKeyCreationResponseDTO execute(Long accountId) {
		if (apiKeyCreationValidator.canBeCreated(accountId)) {
			ApiKeyDTO apiKeyDTO;
			try {
				apiKeyDTO = apiKeyDTOFactory.create(accountId);
			} catch (MultipleApiKeysSimultaneouslyCreatedException e) {
				return createFailedApiKeyCreationResponseDTO();
			}

			return apiKeyCreationResponseDTOFactory.createWithApiKeyDTO(apiKeyDTO);
		} else {
			return createFailedApiKeyCreationResponseDTO();
		}
	}

	private ApiKeyCreationResponseDTO createFailedApiKeyCreationResponseDTO() {
		return apiKeyCreationResponseDTOFactory.createFailedWithReason(ApiKeyCreationResponseDTO.FailReason.TOO_MUCH_KEYS_ALREADY_CREATED);
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import org.springframework.stereotype.Service;

@Service
class ApiKeyCreationResponseDTOFactory {

	ApiKeyCreationResponseDTO createFailedWithReason(ApiKeyCreationResponseDTO.CreationFailReason creationFailReason) {
		return ApiKeyCreationResponseDTO.builder()
				.created(false)
				.creationFailReason(creationFailReason)
				.build();
	}

	ApiKeyCreationResponseDTO createWithApiKeyDTO(ApiKeyDTO apiKeyDTO) {
		return ApiKeyCreationResponseDTO.builder()
				.created(true)
				.apiKeyDTO(apiKeyDTO)
				.build();
	}

}

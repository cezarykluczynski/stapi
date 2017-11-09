package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import org.springframework.stereotype.Service;

@Service
class ApiKeyCreationResponseDTOFactory {

	ApiKeyCreationResponseDTO createFailedWithReason(ApiKeyCreationResponseDTO.FailReason failReason) {
		return ApiKeyCreationResponseDTO.builder()
				.created(false)
				.failReason(failReason)
				.build();
	}

	ApiKeyCreationResponseDTO createWithApiKeyDTO(ApiKeyDTO apiKeyDTO) {
		return ApiKeyCreationResponseDTO.builder()
				.created(true)
				.apiKeyDTO(apiKeyDTO)
				.build();
	}

}

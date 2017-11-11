package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import org.springframework.stereotype.Service;

@Service
class ApiKeyBlockRelatedResponseDTOFactory {

	ApiKeyBlockRelatedResponseDTO createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason failReason) {
		return ApiKeyBlockRelatedResponseDTO.builder()
				.successful(false)
				.failReason(failReason)
				.build();
	}

	ApiKeyBlockRelatedResponseDTO createSuccessful() {
		return ApiKeyBlockRelatedResponseDTO.builder()
				.successful(true)
				.build();
	}
}

package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import org.springframework.stereotype.Service;

@Service
class ApiKeyRemovalResponseDTOFactory {

	ApiKeyRemovalResponseDTO createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason failReason) {
		return ApiKeyRemovalResponseDTO.builder()
				.removed(false)
				.failReason(failReason)
				.build();
	}

	ApiKeyRemovalResponseDTO createSuccessful() {
		return ApiKeyRemovalResponseDTO.builder()
				.removed(true)
				.build();
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
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

package com.cezarykluczynski.stapi.auth.api_key.operation.edit;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class ApiKeyEditResponseDTOFactory {

	ApiKeyEditResponseDTO createUnsuccessful(ApiKeyEditResponseDTO.FailReason failReason) {
		return ApiKeyEditResponseDTO.builder()
				.successful(false)
				.changed(false)
				.failReason(failReason)
				.build();
	}

	ApiKeyEditResponseDTO createSuccessful() {
		return ApiKeyEditResponseDTO.builder()
				.successful(true)
				.changed(true)
				.build();
	}

	ApiKeyEditResponseDTO createUnchanged() {
		return ApiKeyEditResponseDTO.builder()
				.successful(true)
				.changed(false)
				.build();
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
class ApiKeyRemovalExceptionMapper {

	private final ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory;

	public ApiKeyRemovalExceptionMapper(ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory) {
		this.apiKeyRemovalResponseDTOFactory = apiKeyRemovalResponseDTOFactory;
	}

	ApiKeyRemovalResponseDTO map(ApiKeyException apiKeyException) {
		if (apiKeyException instanceof KeyDoesNotExistException) {
			return apiKeyRemovalResponseDTOFactory.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.KEY_DOES_NOT_EXIST);
		} else if (apiKeyException instanceof KeyNotOwnedByAccountException) {
			return apiKeyRemovalResponseDTOFactory.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.KEY_NOT_OWNED_BY_ACCOUNT);
		} else if (apiKeyException instanceof BlockedKeyException) {
			return apiKeyRemovalResponseDTOFactory.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.BLOCKED_KEY);
		} else {
			throw new StapiRuntimeException(String.format("Exception %s not mapped to ApiKeyRemovalResponseDTO.FailReason",
					apiKeyException.getClass().getSimpleName()));
		}
	}

}

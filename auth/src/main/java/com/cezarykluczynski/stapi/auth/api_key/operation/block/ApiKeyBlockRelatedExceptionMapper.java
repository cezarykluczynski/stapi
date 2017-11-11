package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException;
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
class ApiKeyBlockRelatedExceptionMapper {

	private final ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactory;

	public ApiKeyBlockRelatedExceptionMapper(ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactory) {
		this.apiKeyBlockRelatedResponseDTOFactory = apiKeyBlockRelatedResponseDTOFactory;
	}

	public ApiKeyBlockRelatedResponseDTO map(ApiKeyException apiKeyException) {
		if (apiKeyException instanceof KeyDoesNotExistException) {
			return apiKeyBlockRelatedResponseDTOFactory.createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason.KEY_DOES_NOT_EXIST);
		} else if (apiKeyException instanceof KeyNotOwnedByAccountException) {
			return apiKeyBlockRelatedResponseDTOFactory.createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason.KEY_NOT_OWNED_BY_ACCOUNT);
		} else {
			throw new StapiRuntimeException(String.format("Exception %s not mapped to ApiKeyBlockRelatedResponseDTO.FailReason",
					apiKeyException.getClass().getSimpleName()));
		}
	}

}

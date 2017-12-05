package com.cezarykluczynski.stapi.auth.api_key.mapper;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyMapper {

	public ApiKeyDTO map(ApiKey apiKey) {
		if (apiKey == null) {
			throw new StapiRuntimeException("No API key entity to map from");
		}

		ApiKeyDTO apiKeyDTO = new ApiKeyDTO();
		apiKeyDTO.setId(apiKey.getId());
		apiKeyDTO.setApiKey(apiKey.getApiKey());
		apiKeyDTO.setAccountId(apiKey.getAccountId());
		if (apiKeyDTO.getAccountId() == null && apiKey.getAccount() != null) {
			apiKeyDTO.setAccountId(apiKey.getAccount().getId());
		}
		if (apiKey.getThrottle() != null) {
			apiKeyDTO.setRemaining(apiKey.getThrottle().getRemainingHits());
		}
		apiKeyDTO.setLimit(apiKey.getLimit());
		apiKeyDTO.setBlocked(apiKey.getBlocked());
		apiKeyDTO.setUrl(apiKey.getUrl());
		apiKeyDTO.setDescription(apiKey.getDescription());

		return apiKeyDTO;
	}

}

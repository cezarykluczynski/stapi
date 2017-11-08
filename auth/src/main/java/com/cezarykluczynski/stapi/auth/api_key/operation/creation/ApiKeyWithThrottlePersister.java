package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import org.springframework.stereotype.Service;

@Service
class ApiKeyWithThrottlePersister {

	private final ApiKeyRepository apiKeyRepository;

	private final ThrottleRepository throttleRepository;

	private final ApiKeyMapper apiKeyMapper;

	ApiKeyWithThrottlePersister(ApiKeyRepository apiKeyRepository, ThrottleRepository throttleRepository, ApiKeyMapper apiKeyMapper) {
		this.apiKeyRepository = apiKeyRepository;
		this.throttleRepository = throttleRepository;
		this.apiKeyMapper = apiKeyMapper;
	}

	ApiKeyDTO persistAndGetApiKeyDTO(ApiKeyWithThrottleDTO apiKeyWithThrottleDTO) {
		ApiKey apiKey = apiKeyWithThrottleDTO.getApiKey();
		Throttle throttle = apiKeyWithThrottleDTO.getThrottle();
		throttleRepository.save(throttle);
		return apiKeyMapper.map(apiKeyRepository.save(apiKey));
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyWithThrottleBlockService {

	private final ApiKeyRepository apiKeyRepository;

	private final ThrottleRepository throttleRepository;

	public ApiKeyWithThrottleBlockService(ApiKeyRepository apiKeyRepository, ThrottleRepository throttleRepository) {
		this.apiKeyRepository = apiKeyRepository;
		this.throttleRepository = throttleRepository;
	}

	@Transactional
	public synchronized void changeBlockStatus(Long apiKeyId, boolean blocked) {
		ApiKey apiKey = apiKeyRepository.findOne(apiKeyId);
		apiKey.setBlocked(blocked);
		apiKeyRepository.save(apiKey);
		Throttle throttle = throttleRepository.findByApiKey(apiKey.getApiKey());
		throttle.setActive(!blocked);
		throttleRepository.save(throttle);
	}

}

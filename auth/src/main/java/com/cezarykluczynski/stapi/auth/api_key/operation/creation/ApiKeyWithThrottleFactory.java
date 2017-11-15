package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.model.account.repository.AccountRepository;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
class ApiKeyWithThrottleFactory {

	private final AccountRepository accountRepository;

	private final ApiKeyGenerator apiKeyGenerator;

	private final ApiKeyLimitProvider apiKeyLimitProvider;

	ApiKeyWithThrottleFactory(AccountRepository accountRepository, ApiKeyGenerator apiKeyGenerator, ApiKeyLimitProvider apiKeyLimitProvider) {
		this.accountRepository = accountRepository;
		this.apiKeyGenerator = apiKeyGenerator;
		this.apiKeyLimitProvider = apiKeyLimitProvider;
	}

	ApiKeyWithThrottleDTO createForAccount(Long accountId) {
		Integer apiKeyLimit = apiKeyLimitProvider.provideRequestLimitPerKey();
		ApiKey apiKey = createApiKey(accountId, apiKeyLimit);
		Throttle throttle = createThrottle(apiKey, apiKeyLimit);

		return ApiKeyWithThrottleDTO.builder()
				.apiKey(apiKey)
				.throttle(throttle)
				.build();
	}

	private ApiKey createApiKey(Long accountId, Integer apiKeyLimit) {
		ApiKey apiKey = new ApiKey();
		apiKey.setAccount(Preconditions.checkNotNull(accountRepository.findOne(accountId), "Account cannot be null"));
		apiKey.setApiKey(apiKeyGenerator.generate());
		apiKey.setBlocked(false);
		apiKey.setLimit(apiKeyLimit);
		return apiKey;
	}

	private Throttle createThrottle(ApiKey apiKey, Integer apiKeyLimit) {
		Throttle throttle = new Throttle();
		throttle.setApiKey(apiKey.getApiKey());
		throttle.setThrottleType(ThrottleType.API_KEY);
		throttle.setHitsLimit(apiKeyLimit);
		throttle.setRemainingHits(apiKeyLimit);
		throttle.setActive(true);
		return throttle;
	}

}

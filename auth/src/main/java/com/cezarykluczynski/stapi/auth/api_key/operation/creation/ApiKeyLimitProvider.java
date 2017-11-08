package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties;
import org.springframework.stereotype.Service;

@Service
class ApiKeyLimitProvider {

	private final ApiKeyProperties apiKeyProperties;

	ApiKeyLimitProvider(ApiKeyProperties apiKeyProperties) {
		this.apiKeyProperties = apiKeyProperties;
	}

	int provideKeyLimitPerAccount() {
		return apiKeyProperties.getKeyLimitPerAccount();
	}

	int provideRequestLimitPerKey() {
		return apiKeyProperties.getRequestLimitPerKey();
	}

}

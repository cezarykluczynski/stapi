package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
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

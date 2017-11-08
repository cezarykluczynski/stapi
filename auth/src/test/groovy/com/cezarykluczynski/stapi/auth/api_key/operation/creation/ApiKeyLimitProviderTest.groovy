package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.auth.configuration.ApiKeyProperties
import spock.lang.Specification

class ApiKeyLimitProviderTest extends Specification {

	private static final int KEY_LIMIT_PER_ACCOUNT = 5
	private static final int REQUEST_LIMIT_PER_KEY = 10000

	private ApiKeyLimitProvider apiKeyLimitProvider

	void setup() {
		apiKeyLimitProvider = new ApiKeyLimitProvider(new ApiKeyProperties(
				keyLimitPerAccount: KEY_LIMIT_PER_ACCOUNT,
				requestLimitPerKey: REQUEST_LIMIT_PER_KEY))
	}

	void "provides key limit per account"() {
		when:
		int limit = apiKeyLimitProvider.provideKeyLimitPerAccount()

		then:
		limit == KEY_LIMIT_PER_ACCOUNT
	}

	void "provides request limit per ley"() {
		when:
		int limit = apiKeyLimitProvider.provideRequestLimitPerKey()

		then:
		limit == REQUEST_LIMIT_PER_KEY
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType
import spock.lang.Specification

class ApiKeyWithThrottleFactoryTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final int HITS_LIMIT = 10000
	private static final String API_KEY = 'API_KEY'

	private AccountRepository accountRepositoryMock

	private ApiKeyGenerator apiKeyGeneratorMock

	private ApiKeyLimitProvider apiKeyLimitProviderMock

	private ApiKeyWithThrottleFactory apiKeyWithThrottleFactory

	void setup() {
		accountRepositoryMock = Mock()
		apiKeyGeneratorMock = Mock()
		apiKeyLimitProviderMock = Mock()
		apiKeyWithThrottleFactory = new ApiKeyWithThrottleFactory(accountRepositoryMock, apiKeyGeneratorMock, apiKeyLimitProviderMock)
	}

	void "ApiKeyWithThrottle is not created when account cannot be found"() {
		when:
		apiKeyWithThrottleFactory.createForAccount(ACCOUNT_ID)

		then:
		1 * apiKeyLimitProviderMock.provideRequestLimitPerKey() >> HITS_LIMIT
		1 * accountRepositoryMock.findOne(ACCOUNT_ID) >> null
		0 * _
		thrown(NullPointerException)
	}

	void "when account is found, ApiKeyWithThrottle is created"() {
		given:
		Account account = Mock()

		when:
		ApiKeyWithThrottleDTO apiKeyWithThrottleDTO = apiKeyWithThrottleFactory.createForAccount(ACCOUNT_ID)

		then:
		1 * apiKeyLimitProviderMock.provideRequestLimitPerKey() >> HITS_LIMIT
		1 * accountRepositoryMock.findOne(ACCOUNT_ID) >> account
		1 * apiKeyGeneratorMock.generate() >> API_KEY
		0 * _
		apiKeyWithThrottleDTO.apiKey.account == account
		apiKeyWithThrottleDTO.apiKey.apiKey == API_KEY
		!apiKeyWithThrottleDTO.apiKey.blocked
		apiKeyWithThrottleDTO.apiKey.limit == HITS_LIMIT
		apiKeyWithThrottleDTO.throttle.apiKey == API_KEY
		apiKeyWithThrottleDTO.throttle.throttleType == ThrottleType.API_KEY
		apiKeyWithThrottleDTO.throttle.hitsLimit == HITS_LIMIT
		apiKeyWithThrottleDTO.throttle.remainingHits == HITS_LIMIT
		apiKeyWithThrottleDTO.throttle.active
	}

}

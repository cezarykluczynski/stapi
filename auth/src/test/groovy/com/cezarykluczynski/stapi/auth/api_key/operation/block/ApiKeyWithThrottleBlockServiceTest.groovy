package com.cezarykluczynski.stapi.auth.api_key.operation.block

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import org.apache.commons.lang3.RandomUtils
import spock.lang.Specification

class ApiKeyWithThrottleBlockServiceTest extends Specification {

	private static final Long API_KEY_ID = 15L
	private static final boolean BLOCKED = RandomUtils.nextBoolean()
	private static final String API_KEY = 'API_KEY'

	private ApiKeyRepository apiKeyRepositoryMock

	private ThrottleRepository throttleRepositoryMock

	private ApiKeyWithThrottleBlockService apiKeyWithThrottleBlockService

	void setup() {
		apiKeyRepositoryMock = Mock()
		throttleRepositoryMock = Mock()
		apiKeyWithThrottleBlockService = new ApiKeyWithThrottleBlockService(apiKeyRepositoryMock, throttleRepositoryMock)
	}

	void "changes block status"() {
		given:
		ApiKey apiKey = new ApiKey(apiKey: API_KEY)
		Throttle throttle = new Throttle()

		when:
		apiKeyWithThrottleBlockService.changeBlockStatus(API_KEY_ID, BLOCKED)

		then:
		1 * apiKeyRepositoryMock.findOne(API_KEY_ID) >> apiKey
		1 * apiKeyRepositoryMock.save(_ as ApiKey) >> { ApiKey apiKeyInput ->
			assert apiKeyInput == apiKey
			assert apiKeyInput.blocked == BLOCKED
		}
		1 * throttleRepositoryMock.findByApiKey(API_KEY) >> throttle
		1 * throttleRepositoryMock.save(_ as Throttle) >> { Throttle throttleInput ->
			assert throttleInput == throttle
			assert throttleInput.active == !BLOCKED
		}
	}

}

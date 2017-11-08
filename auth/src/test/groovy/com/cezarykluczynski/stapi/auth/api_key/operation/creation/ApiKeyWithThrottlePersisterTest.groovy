package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import spock.lang.Specification

class ApiKeyWithThrottlePersisterTest extends Specification {

	private ApiKeyRepository apiKeyRepositoryMock

	private ThrottleRepository throttleRepositoryMock

	private ApiKeyMapper apiKeyMapperMock

	private ApiKeyWithThrottlePersister apiKeyWithThrottlePersister

	void setup() {
		apiKeyRepositoryMock = Mock()
		throttleRepositoryMock = Mock()
		apiKeyMapperMock = Mock()
		apiKeyWithThrottlePersister = new ApiKeyWithThrottlePersister(apiKeyRepositoryMock, throttleRepositoryMock, apiKeyMapperMock)
	}

	void "persists throttle and API key, then maps API key to DTO"() {
		given:
		ApiKey apiKey = Mock()
		Throttle throttle = Mock()
		ApiKeyWithThrottleDTO apiKeyWithThrottleDTO = ApiKeyWithThrottleDTO.builder()
				.apiKey(apiKey)
				.throttle(throttle)
				.build()
		ApiKeyDTO apiKeyDTO = Mock()

		when:
		ApiKeyDTO apiKeyDTOOutput = apiKeyWithThrottlePersister.persistAndGetApiKeyDTO(apiKeyWithThrottleDTO)

		then:
		1 * throttleRepositoryMock.save(throttle) >> throttle
		1 * apiKeyRepositoryMock.save(apiKey) >> apiKey
		1 * apiKeyMapperMock.map(apiKey) >> apiKeyDTO
		0 * _
		apiKeyDTOOutput == apiKeyDTO
	}

}

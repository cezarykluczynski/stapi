package com.cezarykluczynski.stapi.auth.api_key.operation.removal

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import spock.lang.Specification

class ApiKeyRemovalOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L
	private static final String API_KEY = 'API_KEY'

	private ApiKeyRemovalValidator apiKeyRemovalValidatorMock

	private ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapperMock

	private ApiKeyRepository apiKeyRepositoryMock

	private ThrottleRepository throttleRepositoryMock

	private ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactoryMock

	private ApiKeyRemovalOperation apiKeyRemovalOperation

	void setup() {
		apiKeyRemovalValidatorMock = Mock()
		apiKeyRemovalExceptionMapperMock = Mock()
		apiKeyRepositoryMock = Mock()
		throttleRepositoryMock = Mock()
		apiKeyRemovalResponseDTOFactoryMock = Mock()
		apiKeyRemovalOperation = new ApiKeyRemovalOperation(apiKeyRemovalValidatorMock, apiKeyRemovalExceptionMapperMock, apiKeyRepositoryMock,
				throttleRepositoryMock, apiKeyRemovalResponseDTOFactoryMock)
	}

	void "when validator throws exception, it is mapped to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyException apiKeyException = new ApiKeyException('')
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRemovalValidatorMock.validate(ACCOUNT_ID, API_KEY_ID) >> {
			throw apiKeyException
		}
		1 * apiKeyRemovalExceptionMapperMock.map(apiKeyException) >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "when validator does not throw exception, api key is removed"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()
		ApiKey apiKey = new ApiKey(apiKey: API_KEY)

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRemovalValidatorMock.validate(ACCOUNT_ID, API_KEY_ID)
		1 * apiKeyRepositoryMock.findOne(API_KEY_ID) >> apiKey
		1 * apiKeyRepositoryMock.delete(API_KEY_ID)
		1 * throttleRepositoryMock.deleteByApiKey(API_KEY)
		1 * apiKeyRemovalResponseDTOFactoryMock.createSuccessful() >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

}

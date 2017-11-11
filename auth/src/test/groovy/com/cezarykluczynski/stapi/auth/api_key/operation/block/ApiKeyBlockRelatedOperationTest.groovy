package com.cezarykluczynski.stapi.auth.api_key.operation.block

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException
import org.apache.commons.lang3.RandomUtils
import spock.lang.Specification

class ApiKeyBlockRelatedOperationTest extends Specification {

	private static final boolean BLOCKED = RandomUtils.nextBoolean()
	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyBlockRelatedValidator apiKeyBlockRelatedValidatorMock

	private ApiKeyBlockRelatedExceptionMapper apiKeyBlockRelatedExceptionMapperMock

	private ApiKeyWithThrottleBlockService apiKeyWithThrottleBlockServiceMock

	private ApiKeyBlockRelatedResponseDTOFactory apiKeyBlockRelatedResponseDTOFactoryMock

	private ApiKeyBlockRelatedOperation apiKeyBlockRelatedOperation

	void setup() {
		apiKeyBlockRelatedValidatorMock = Mock()
		apiKeyBlockRelatedExceptionMapperMock = Mock()
		apiKeyWithThrottleBlockServiceMock = Mock()
		apiKeyBlockRelatedResponseDTOFactoryMock = Mock()
		apiKeyBlockRelatedOperation = new ApiKeyBlockRelatedOperation(apiKeyBlockRelatedValidatorMock, apiKeyBlockRelatedExceptionMapperMock,
				apiKeyWithThrottleBlockServiceMock, apiKeyBlockRelatedResponseDTOFactoryMock)
	}

	void "when validator throws exception, it is mapped to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyException apiKeyException = new ApiKeyException('')
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyBlockRelatedOperation.execute(ACCOUNT_ID, API_KEY_ID, BLOCKED)

		then:
		1 * apiKeyBlockRelatedValidatorMock.validate(ACCOUNT_ID, API_KEY_ID) >> {
			throw apiKeyException
		}
		1 * apiKeyBlockRelatedExceptionMapperMock.map(apiKeyException) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "when validator does not throw exception, api key is removed"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyBlockRelatedOperation.execute(ACCOUNT_ID, API_KEY_ID, BLOCKED)

		then:
		1 * apiKeyBlockRelatedValidatorMock.validate(ACCOUNT_ID, API_KEY_ID)
		1 * apiKeyWithThrottleBlockServiceMock.changeBlockStatus(API_KEY_ID, BLOCKED)
		1 * apiKeyBlockRelatedResponseDTOFactoryMock.createSuccessful() >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

}

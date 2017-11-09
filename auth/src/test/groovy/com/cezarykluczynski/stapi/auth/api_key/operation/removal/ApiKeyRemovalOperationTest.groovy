package com.cezarykluczynski.stapi.auth.api_key.operation.removal

import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import spock.lang.Specification

class ApiKeyRemovalOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyRemovalValidator apiKeyRemovalValidatorMock

	private ApiKeyRemovalExceptionMapper apiKeyRemovalExceptionMapperMock

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactoryMock

	private ApiKeyRemovalOperation apiKeyRemovalOperation

	void setup() {
		apiKeyRemovalValidatorMock = Mock()
		apiKeyRemovalExceptionMapperMock = Mock()
		apiKeyRepositoryMock = Mock()
		apiKeyRemovalResponseDTOFactoryMock = Mock()
		apiKeyRemovalOperation = new ApiKeyRemovalOperation(apiKeyRemovalValidatorMock, apiKeyRemovalExceptionMapperMock, apiKeyRepositoryMock,
				apiKeyRemovalResponseDTOFactoryMock)
	}

	void "when validator throws exception, it is mapped to ApiKeyRemovalResponseDTO"() {
		given:
		ApiKeyRemovalException apiKeyRemovalException = new ApiKeyRemovalException('')
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRemovalValidatorMock.validate(ACCOUNT_ID, API_KEY_ID) >> {
			throw apiKeyRemovalException
		}
		1 * apiKeyRemovalExceptionMapperMock.map(apiKeyRemovalException) >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "when validator does not throw exception, api key is removed"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeyRemovalOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRemovalValidatorMock.validate(ACCOUNT_ID, API_KEY_ID)
		1 * apiKeyRepositoryMock.delete(API_KEY_ID)
		1 * apiKeyRemovalResponseDTOFactoryMock.createSuccessful() >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

}

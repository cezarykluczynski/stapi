package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import spock.lang.Specification

class ApiKeyCreationOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private ApiKeyCreationValidator apiKeyCreationValidatorMock

	private ApiKeyDTOFactory apiKeyDTOFactoryMock

	private ApiKeyCreationResponseDTOFactory apiKeyCreationResponseDTOFactoryMock

	private ApiKeyCreationOperation apiKeyCreationOperation

	void setup() {
		apiKeyCreationValidatorMock = Mock()
		apiKeyDTOFactoryMock = Mock()
		apiKeyCreationResponseDTOFactoryMock = Mock()
		apiKeyCreationOperation = new ApiKeyCreationOperation(apiKeyCreationValidatorMock, apiKeyDTOFactoryMock, apiKeyCreationResponseDTOFactoryMock)
	}

	void "when key can be created, successful response is returned"() {
		given:
		ApiKeyDTO apiKeyDTO = Mock()
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = apiKeyCreationOperation.execute(ACCOUNT_ID)

		then:
		1 * apiKeyCreationValidatorMock.canBeCreated(ACCOUNT_ID) >> true
		1 * apiKeyDTOFactoryMock.create(ACCOUNT_ID) >> apiKeyDTO
		1 * apiKeyCreationResponseDTOFactoryMock.createWithApiKeyDTO(apiKeyDTO) >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

	void "when key cannot be created because of exception thrown from factory, unsuccessful response is returned"() {
		given:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = apiKeyCreationOperation.execute(ACCOUNT_ID)

		then:
		1 * apiKeyCreationValidatorMock.canBeCreated(ACCOUNT_ID) >> true
		1 * apiKeyDTOFactoryMock.create(ACCOUNT_ID) >> {
			throw new MultipleApiKeysSimultaneouslyCreatedException()
		}
		1 *  apiKeyCreationResponseDTOFactoryMock
				.createFailedWithReason(ApiKeyCreationResponseDTO.FailReason.TOO_MUCH_KEYS_ALREADY_CREATED) >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

	void "when key cannot be created, unsuccessful response is returned"() {
		given:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = apiKeyCreationOperation.execute(ACCOUNT_ID)

		then:
		1 * apiKeyCreationValidatorMock.canBeCreated(ACCOUNT_ID) >> false
		1 *  apiKeyCreationResponseDTOFactoryMock
				.createFailedWithReason(ApiKeyCreationResponseDTO.FailReason.TOO_MUCH_KEYS_ALREADY_CREATED) >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

}

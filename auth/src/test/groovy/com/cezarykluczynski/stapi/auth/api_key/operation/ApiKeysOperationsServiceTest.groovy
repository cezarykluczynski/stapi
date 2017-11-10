package com.cezarykluczynski.stapi.auth.api_key.operation

import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO
import spock.lang.Specification

class ApiKeysOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeysReadOperation apiKeysReadOperationMock

	private ApiKeyCreationOperation apiKeyCreationOperationMock

	private ApiKeyRemovalOperation apiKeyRemovalOperationMock

	private ApiKeysOperationsService apiKeysOperationsService

	void setup() {
		apiKeysReadOperationMock = Mock()
		apiKeyCreationOperationMock = Mock()
		apiKeyRemovalOperationMock = Mock()
		apiKeysOperationsService = new ApiKeysOperationsService(apiKeysReadOperationMock, apiKeyCreationOperationMock, apiKeyRemovalOperationMock)
	}

	void "gets all keys"() {
		given:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysOperationsService.getAll(ACCOUNT_ID)

		then:
		1 * apiKeysReadOperationMock.execute(ACCOUNT_ID) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

	void "creates key"() {
		given:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = apiKeysOperationsService.create(ACCOUNT_ID)

		then:
		1 * apiKeyCreationOperationMock.execute(ACCOUNT_ID) >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

	void "removes key"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeysOperationsService.remove(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRemovalOperationMock.execute(ACCOUNT_ID, API_KEY_ID) >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

}

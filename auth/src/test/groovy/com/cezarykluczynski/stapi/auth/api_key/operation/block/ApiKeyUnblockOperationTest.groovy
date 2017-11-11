package com.cezarykluczynski.stapi.auth.api_key.operation.block

import spock.lang.Specification

class ApiKeyUnblockOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyBlockRelatedOperation apiKeyBlockRelatedOperationMock

	private ApiKeyUnblockOperation apiKeyUnblockOperation

	void setup() {
		apiKeyBlockRelatedOperationMock = Mock()
		apiKeyUnblockOperation = new ApiKeyUnblockOperation(apiKeyBlockRelatedOperationMock)
	}

	void "passes request to ApiKeyBlockRelatedOperation"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyUnblockOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyBlockRelatedOperationMock.execute(ACCOUNT_ID, API_KEY_ID, false) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

}

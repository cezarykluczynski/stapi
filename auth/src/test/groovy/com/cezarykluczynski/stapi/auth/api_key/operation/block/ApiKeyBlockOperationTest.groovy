package com.cezarykluczynski.stapi.auth.api_key.operation.block

import spock.lang.Specification

class ApiKeyBlockOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyBlockRelatedOperation apiKeyBlockRelatedOperationMock

	private ApiKeyBlockOperation apiKeyBlockOperation

	void setup() {
		apiKeyBlockRelatedOperationMock = Mock()
		apiKeyBlockOperation = new ApiKeyBlockOperation(apiKeyBlockRelatedOperationMock)
	}

	void "passes request to ApiKeyBlockRelatedOperation"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyBlockOperation.execute(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyBlockRelatedOperationMock.execute(ACCOUNT_ID, API_KEY_ID, true) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

}

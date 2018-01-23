package com.cezarykluczynski.stapi.auth.api_key.operation

import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO
import spock.lang.Specification

class ApiKeyAdminOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 123
	private static final Long API_KEY_ID = 456

	private ApiKeysOperationsService apiKeysOperationsServiceMock

	private ApiKeyAdminOperationsService apiKeyAdminOperationsService

	void setup() {
		apiKeysOperationsServiceMock = Mock()
		apiKeyAdminOperationsService = new ApiKeyAdminOperationsService(apiKeysOperationsServiceMock)
	}

	void "gets page"() {
		given:
		ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeyAdminOperationsService.search(apiKeysSearchCriteriaDTO)

		then:
		1 * apiKeysOperationsServiceMock.search(apiKeysSearchCriteriaDTO) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

	void "blocks API key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyAdminOperationsService.block(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeysOperationsServiceMock.block(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "unblocks API key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeyAdminOperationsService.unblock(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeysOperationsServiceMock.unblock(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation

import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyUnblockOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyDetailsDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysReadPageOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalOperation
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO
import spock.lang.Specification

class ApiKeysOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeysReadOperation apiKeysReadOperationMock

	private ApiKeysReadPageOperation apiKeysReadPageOperationMock

	private ApiKeyCreationOperation apiKeyCreationOperationMock

	private ApiKeyRemovalOperation apiKeyRemovalOperationMock

	private ApiKeyBlockOperation apiKeyBlockOperationMock

	private ApiKeyUnblockOperation apiKeyUnblockOperationMock

	private ApiKeyEditOperation apiKeyEditOperationMock

	private ApiKeysOperationsService apiKeysOperationsService

	void setup() {
		apiKeysReadOperationMock = Mock()
		apiKeysReadPageOperationMock = Mock()
		apiKeyCreationOperationMock = Mock()
		apiKeyRemovalOperationMock = Mock()
		apiKeyBlockOperationMock = Mock()
		apiKeyUnblockOperationMock = Mock()
		apiKeyEditOperationMock = Mock()
		apiKeysOperationsService = new ApiKeysOperationsService(apiKeysReadOperationMock, apiKeysReadPageOperationMock, apiKeyCreationOperationMock,
				apiKeyRemovalOperationMock, apiKeyBlockOperationMock, apiKeyUnblockOperationMock, apiKeyEditOperationMock)
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

	void "gets page"() {
		given:
		ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysOperationsService.search(apiKeysSearchCriteriaDTO)

		then:
		1 * apiKeysReadPageOperationMock.execute(apiKeysSearchCriteriaDTO) >> apiKeyReadResponseDTO
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

	void "blocks key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeysOperationsService.block(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyBlockOperationMock.execute(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "unblocks key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = apiKeysOperationsService.unblock(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyUnblockOperationMock.execute(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "edits key details"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = Mock()
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = apiKeysOperationsService.edit(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyEditOperationMock.execute(ACCOUNT_ID, apiKeyDetailsDTO) >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
	}

}

package com.cezarykluczynski.stapi.auth.api_key.operation

import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyDetailsDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import spock.lang.Specification

class ApiKeysOwnOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L
	private static final String URL = 'URL'
	private static final String DESCRIPTION = 'DESCRIPTION'

	private OAuthSessionHolder oAuthSessionHolderMock

	private ApiKeysOperationsService apiKeysOperationsServiceMock

	private ApiKeysOwnOperationsService apiKeysOwnOperationsService

	void setup() {
		oAuthSessionHolderMock = Mock()
		apiKeysOperationsServiceMock = Mock()
		apiKeysOwnOperationsService = new ApiKeysOwnOperationsService(oAuthSessionHolderMock, apiKeysOperationsServiceMock)
	}

	void "gets own keys"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysOwnOperationsService.all

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * apiKeysOperationsServiceMock.getAll(ACCOUNT_ID) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

	void "creates own key"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = apiKeysOwnOperationsService.create()

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * apiKeysOperationsServiceMock.create(ACCOUNT_ID) >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

	void "removes own key"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = apiKeysOwnOperationsService.remove(API_KEY_ID)

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * apiKeysOperationsServiceMock.remove(ACCOUNT_ID, API_KEY_ID) >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

	void "edits own key"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(accountId: ACCOUNT_ID)
		ApiKeyEditResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyRemovalResponseDTOOutput = apiKeysOwnOperationsService.edit(API_KEY_ID, URL, DESCRIPTION)

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * apiKeysOperationsServiceMock.edit(ACCOUNT_ID, _ as ApiKeyDetailsDTO) >> { Long accountId, ApiKeyDetailsDTO apiKeyDetailsDTO ->
			assert apiKeyDetailsDTO.apiKeyId == API_KEY_ID
			assert apiKeyDetailsDTO.url == URL
			assert apiKeyDetailsDTO.description == DESCRIPTION
			apiKeyRemovalResponseDTO
		}
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

}

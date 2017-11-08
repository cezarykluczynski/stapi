package com.cezarykluczynski.stapi.auth.api_key.operation

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import spock.lang.Specification

class ApiKeysOwnOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

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
		List<ApiKeyDTO> apiKeyDTOList = Mock()

		when:
		List<ApiKeyDTO> apiKeyDTOListOutput = apiKeysOwnOperationsService.all

		then:
		1 * oAuthSessionHolderMock.OAuthSession >> oAuthSession
		1 * apiKeysOperationsServiceMock.getAll(ACCOUNT_ID) >> apiKeyDTOList
		0 * _
		apiKeyDTOListOutput == apiKeyDTOList
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

}

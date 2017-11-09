package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeysOwnOperationsService
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO
import spock.lang.Specification

class PanelApiKeysEndpointTest extends Specification {

	private static final Long API_KEY_ID = 15L

	private ApiKeysOwnOperationsService apiKeysOwnOperationsServiceMock

	private PanelApiKeysEndpoint panelApiKeysEndpoint

	void setup() {
		apiKeysOwnOperationsServiceMock = Mock()
		panelApiKeysEndpoint = new PanelApiKeysEndpoint(apiKeysOwnOperationsServiceMock)
	}

	void "gets all api keys"() {
		given:
		List<ApiKeyDTO> apiKeyDTOList = Mock()

		when:
		List<ApiKeyDTO> all = panelApiKeysEndpoint.all

		then:
		1 * apiKeysOwnOperationsServiceMock.all >> apiKeyDTOList
		0 * _
		all == apiKeyDTOList
	}

	void "creates api key"() {
		given:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTOOutput = panelApiKeysEndpoint.create()

		then:
		1 * apiKeysOwnOperationsServiceMock.create() >> apiKeyCreationResponseDTO
		0 * _
		apiKeyCreationResponseDTOOutput == apiKeyCreationResponseDTO
	}

	void "removes api key"() {
		given:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = Mock()

		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTOOutput = panelApiKeysEndpoint.remove(API_KEY_ID)

		then:
		1 * apiKeysOwnOperationsServiceMock.remove(API_KEY_ID) >> apiKeyRemovalResponseDTO
		0 * _
		apiKeyRemovalResponseDTOOutput == apiKeyRemovalResponseDTO
	}

}

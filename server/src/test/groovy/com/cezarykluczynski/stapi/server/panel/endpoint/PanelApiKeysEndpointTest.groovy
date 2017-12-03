package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeysOwnOperationsService
import com.cezarykluczynski.stapi.auth.api_key.operation.creation.ApiKeyCreationResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.edit.ApiKeyEditResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.removal.ApiKeyRemovalResponseDTO
import spock.lang.Specification

class PanelApiKeysEndpointTest extends Specification {

	private static final Long API_KEY_ID = 15L
	private static final String URL = 'URL'
	private static final String DESCRIPTION = 'DESCRIPTION'

	private ApiKeysOwnOperationsService apiKeysOwnOperationsServiceMock

	private PanelApiKeysEndpoint panelApiKeysEndpoint

	void setup() {
		apiKeysOwnOperationsServiceMock = Mock()
		panelApiKeysEndpoint = new PanelApiKeysEndpoint(apiKeysOwnOperationsServiceMock)
	}

	void "gets all api keys"() {
		given:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = panelApiKeysEndpoint.all

		then:
		1 * apiKeysOwnOperationsServiceMock.all >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
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

	void "edits api key"() {
		given:
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = panelApiKeysEndpoint.edit(API_KEY_ID, URL, DESCRIPTION)

		then:
		1 * apiKeysOwnOperationsServiceMock.edit(API_KEY_ID, URL, DESCRIPTION) >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
	}

}

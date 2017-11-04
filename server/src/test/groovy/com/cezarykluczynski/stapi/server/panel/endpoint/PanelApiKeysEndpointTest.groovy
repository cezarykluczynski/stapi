package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.service.ApiKeysOwnOperationsService
import spock.lang.Specification

class PanelApiKeysEndpointTest extends Specification {

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

}

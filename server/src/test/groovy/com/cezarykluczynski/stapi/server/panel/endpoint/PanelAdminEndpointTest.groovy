package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeyAdminOperationsService
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import spock.lang.Specification

class PanelAdminEndpointTest extends Specification {

	private static final int PAGE_NUMBER = 4
	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyAdminOperationsService apiKeyAdminOperationsServiceMock

	private PanelAdminEndpoint panelAdminEndpoint

	void setup() {
		apiKeyAdminOperationsServiceMock = Mock()
		panelAdminEndpoint = new PanelAdminEndpoint(apiKeyAdminOperationsServiceMock)
	}

	void "reads API keys page"() {
		given:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = panelAdminEndpoint.readApiKeysPage(PAGE_NUMBER)

		then:
		1 * apiKeyAdminOperationsServiceMock.getPage(PAGE_NUMBER) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

	void "blocks API key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = panelAdminEndpoint.blockApiKey(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyAdminOperationsServiceMock.block(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

	void "unblocks API key"() {
		given:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTO = Mock()

		when:
		ApiKeyBlockRelatedResponseDTO apiKeyBlockRelatedResponseDTOOutput = panelAdminEndpoint.unblockApiKey(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyAdminOperationsServiceMock.unblock(ACCOUNT_ID, API_KEY_ID) >> apiKeyBlockRelatedResponseDTO
		0 * _
		apiKeyBlockRelatedResponseDTOOutput == apiKeyBlockRelatedResponseDTO
	}

}

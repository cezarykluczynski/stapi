package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.auth.account.operation.AccountAdminOperationsService
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountReadResponseDTO
import com.cezarykluczynski.stapi.auth.account.operation.read.AccountsSearchCriteriaDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.ApiKeyAdminOperationsService
import com.cezarykluczynski.stapi.auth.api_key.operation.block.ApiKeyBlockRelatedResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeyReadResponseDTO
import com.cezarykluczynski.stapi.auth.api_key.operation.read.ApiKeysSearchCriteriaDTO
import spock.lang.Specification

class PanelAdminEndpointTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L

	private ApiKeyAdminOperationsService apiKeyAdminOperationsServiceMock

	private AccountAdminOperationsService accountAdminOperationsServiceMock

	private PanelAdminEndpoint panelAdminEndpoint

	void setup() {
		apiKeyAdminOperationsServiceMock = Mock()
		accountAdminOperationsServiceMock = Mock()
		panelAdminEndpoint = new PanelAdminEndpoint(apiKeyAdminOperationsServiceMock, accountAdminOperationsServiceMock)
	}

	void "searches for API keys"() {
		given:
		ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = panelAdminEndpoint.searchApiKeys(apiKeysSearchCriteriaDTO)

		then:
		1 * apiKeyAdminOperationsServiceMock.search(apiKeysSearchCriteriaDTO) >> apiKeyReadResponseDTO
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

	void "searches for accounts"() {
		given:
		AccountsSearchCriteriaDTO accountsSearchCriteriaDTO = Mock()
		AccountReadResponseDTO accountReadResponseDTO = Mock()

		when:
		AccountReadResponseDTO accountReadResponseDTOOutput = panelAdminEndpoint.searchAccounts(accountsSearchCriteriaDTO)

		then:
		1 * accountAdminOperationsServiceMock.search(accountsSearchCriteriaDTO) >> accountReadResponseDTO
		0 * _
		accountReadResponseDTOOutput == accountReadResponseDTO
	}

}

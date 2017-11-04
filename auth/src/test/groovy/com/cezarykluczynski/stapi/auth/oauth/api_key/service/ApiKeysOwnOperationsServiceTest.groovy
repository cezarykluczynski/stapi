package com.cezarykluczynski.stapi.auth.oauth.api_key.service

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.service.ApiKeysOperationsService
import com.cezarykluczynski.stapi.auth.api_key.service.ApiKeysOwnOperationsService
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSession
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import spock.lang.Specification

class ApiKeysOwnOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 5L

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

}

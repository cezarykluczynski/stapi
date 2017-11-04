package com.cezarykluczynski.stapi.auth.oauth.api_key.service

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper
import com.cezarykluczynski.stapi.auth.api_key.service.ApiKeysOperationsService
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class ApiKeysOperationsServiceTest extends Specification {

	private static final Long ACCOUNT_ID = 5L

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyMapper apiKeyMapperMock

	private ApiKeysOperationsService apiKeysOperationsService

	void setup() {
		apiKeyRepositoryMock = Mock()
		apiKeyMapperMock = Mock()
		apiKeysOperationsService = new ApiKeysOperationsService(apiKeyRepositoryMock, apiKeyMapperMock)
	}

	void "gets all keys"() {
		given:
		ApiKey apiKey1 = Mock()
		ApiKey apiKey2 = Mock()
		ApiKeyDTO apiKeyDTO1 = Mock()
		ApiKeyDTO apiKeyDTO2 = Mock()

		when:
		List<ApiKeyDTO> apiKeyDTOList = apiKeysOperationsService.getAll(ACCOUNT_ID)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(apiKey1, apiKey2)
		1 * apiKeyMapperMock.map(apiKey1) >> apiKeyDTO1
		1 * apiKeyMapperMock.map(apiKey2) >> apiKeyDTO2
		0 * _
		apiKeyDTOList.size() == 2
		apiKeyDTOList[0] == apiKeyDTO1
		apiKeyDTOList[1] == apiKeyDTO2
	}

}

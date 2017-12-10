package com.cezarykluczynski.stapi.auth.api_key.operation.read

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper
import com.cezarykluczynski.stapi.auth.api_key.mapper.PagerMapper
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.util.wrapper.Pager
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

class ApiKeysReadPageOperationTest extends Specification {

	private ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapperMock

	private ApiKeysReader apiKeysReaderMock

	private ApiKeyMapper apiKeyMapperMock

	private PagerMapper pageMapperMock

	private ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactoryMock

	private ApiKeysReadPageOperation apiKeysReadPageOperation

	void setup() {
		apiKeysReadCriteriaMapperMock = Mock()
		apiKeysReaderMock = Mock()
		apiKeyMapperMock = Mock()
		pageMapperMock = Mock()
		apiKeyReadResponseDTOFactoryMock = Mock()
		apiKeysReadPageOperation = new ApiKeysReadPageOperation(apiKeysReadCriteriaMapperMock, apiKeysReaderMock, apiKeyMapperMock, pageMapperMock,
				apiKeyReadResponseDTOFactoryMock)
	}

	void "successful response is returned"() {
		given:
		ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO = Mock()
		ApiKeysReadCriteria apiKeysReadCriteria = Mock()
		ApiKey apiKey = Mock()
		List<ApiKey> apiKeys = Lists.newArrayList(apiKey)
		Page<ApiKey> apiKeyPage = new PageImpl<>(apiKeys)
		Pager pager = Mock()
		ApiKeyDTO apiKeyDTO = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysReadPageOperation.execute(apiKeysSearchCriteriaDTO)

		then:
		1 * apiKeysReadCriteriaMapperMock.fromSearchCriteria(apiKeysSearchCriteriaDTO) >> apiKeysReadCriteria
		1 * apiKeysReaderMock.execute(apiKeysReadCriteria) >> apiKeyPage
		1 * apiKeyMapperMock.map(apiKey) >> apiKeyDTO
		1 * pageMapperMock.map(apiKeyPage) >> pager
		1 * apiKeyReadResponseDTOFactoryMock.createWithApiKeysAndPager(Lists.newArrayList(apiKeyDTO), pager) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

}

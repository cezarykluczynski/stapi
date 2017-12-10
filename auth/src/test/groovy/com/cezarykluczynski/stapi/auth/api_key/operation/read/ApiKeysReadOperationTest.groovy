package com.cezarykluczynski.stapi.auth.api_key.operation.read

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.auth.api_key.mapper.ApiKeyMapper
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

class ApiKeysReadOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapperMock

	private ApiKeysReader apiKeysReaderMock

	private ApiKeyPageAccountSpecification apiKeyPageAccountSpecificationMock

	private ApiKeyMapper apiKeyMapperMock

	private ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactoryMock

	private ApiKeysReadOperation apiKeysReadOperation

	void setup() {
		apiKeysReadCriteriaMapperMock = Mock()
		apiKeysReaderMock = Mock()
		apiKeyPageAccountSpecificationMock = Mock()
		apiKeyMapperMock = Mock()
		apiKeyReadResponseDTOFactoryMock = Mock()
		apiKeysReadOperation = new ApiKeysReadOperation(apiKeysReadCriteriaMapperMock, apiKeysReaderMock, apiKeyPageAccountSpecificationMock,
				apiKeyMapperMock, apiKeyReadResponseDTOFactoryMock)
	}

	void "when specification is not met, failed response is returned"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = Mock()
		Page<ApiKey> apiKeyPage = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysReadOperation.execute(ACCOUNT_ID)

		then:
		1 * apiKeysReadCriteriaMapperMock.fromAccountId(ACCOUNT_ID) >> apiKeysReadCriteria
		1 * apiKeysReaderMock.execute(apiKeysReadCriteria) >> apiKeyPage
		1 * apiKeyPageAccountSpecificationMock.isSatisfiedBy(apiKeyPage) >> false
		1 * apiKeyReadResponseDTOFactoryMock
				.createFailedWithReason(ApiKeyReadResponseDTO.FailReason.TOO_MUCH_ENTRIES_FOR_A_SINGLE_PAGE) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

	void "when specification is met, successful response is returned"() {
		given:
		ApiKeysReadCriteria apiKeysReadCriteria = Mock()
		ApiKey apiKey = Mock()
		List<ApiKey> apiKeys = Lists.newArrayList(apiKey)
		Page<ApiKey> apiKeyPage = new PageImpl<>(apiKeys)
		ApiKeyDTO apiKeyDTO = Mock()
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTOOutput = apiKeysReadOperation.execute(ACCOUNT_ID)

		then:
		1 * apiKeysReadCriteriaMapperMock.fromAccountId(ACCOUNT_ID) >> apiKeysReadCriteria
		1 * apiKeysReaderMock.execute(apiKeysReadCriteria) >> apiKeyPage
		1 * apiKeyPageAccountSpecificationMock.isSatisfiedBy(apiKeyPage) >> true
		1 * apiKeyMapperMock.map(apiKey) >> apiKeyDTO
		1 * apiKeyReadResponseDTOFactoryMock.createWithApiKeys(Lists.newArrayList(apiKeyDTO)) >> apiKeyReadResponseDTO
		0 * _
		apiKeyReadResponseDTOOutput == apiKeyReadResponseDTO
	}

}

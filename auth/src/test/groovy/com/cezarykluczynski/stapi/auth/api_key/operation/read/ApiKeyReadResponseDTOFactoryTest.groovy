package com.cezarykluczynski.stapi.auth.api_key.operation.read

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import com.cezarykluczynski.stapi.util.wrapper.Pager
import spock.lang.Specification

class ApiKeyReadResponseDTOFactoryTest extends Specification {

	private ApiKeyReadResponseDTOFactory apiKeyReadResponseDTOFactory

	void setup() {
		apiKeyReadResponseDTOFactory = new ApiKeyReadResponseDTOFactory()
	}

	void "creates failed response"() {
		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = apiKeyReadResponseDTOFactory
				.createFailedWithReason(ApiKeyReadResponseDTO.FailReason.TOO_MUCH_ENTRIES_FOR_A_SINGLE_PAGE)

		then:
		!apiKeyReadResponseDTO.read
		apiKeyReadResponseDTO.failReason == ApiKeyReadResponseDTO.FailReason.TOO_MUCH_ENTRIES_FOR_A_SINGLE_PAGE
		apiKeyReadResponseDTO.apiKeys == null
		apiKeyReadResponseDTO.pager == null
	}

	void "creates response with API keys"() {
		given:
		List<ApiKeyDTO> apiKeys = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = apiKeyReadResponseDTOFactory.createWithApiKeys(apiKeys)

		then:
		apiKeyReadResponseDTO.read
		apiKeyReadResponseDTO.failReason == null
		apiKeyReadResponseDTO.apiKeys == apiKeys
		apiKeyReadResponseDTO.pager == null
	}

	void "creates response with API keys and pager"() {
		given:
		List<ApiKeyDTO> apiKeys = Mock()
		Pager pager = Mock()

		when:
		ApiKeyReadResponseDTO apiKeyReadResponseDTO = apiKeyReadResponseDTOFactory.createWithApiKeysAndPager(apiKeys, pager)

		then:
		apiKeyReadResponseDTO.read
		apiKeyReadResponseDTO.failReason == null
		apiKeyReadResponseDTO.apiKeys == apiKeys
		apiKeyReadResponseDTO.pager == pager
	}

}

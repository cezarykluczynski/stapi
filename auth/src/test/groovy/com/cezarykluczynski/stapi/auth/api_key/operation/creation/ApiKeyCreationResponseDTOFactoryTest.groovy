package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import spock.lang.Specification

class ApiKeyCreationResponseDTOFactoryTest extends Specification {

	private ApiKeyCreationResponseDTOFactory apiKeyCreationResponseDTOFactory

	void setup() {
		apiKeyCreationResponseDTOFactory = new ApiKeyCreationResponseDTOFactory()
	}

	void "creates failed response"() {
		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = apiKeyCreationResponseDTOFactory
				.createFailedWithReason(ApiKeyCreationResponseDTO.FailReason.TOO_MUCH_KEYS_ALREADY_CREATED)

		then:
		!apiKeyCreationResponseDTO.created
		apiKeyCreationResponseDTO.failReason == ApiKeyCreationResponseDTO.FailReason.TOO_MUCH_KEYS_ALREADY_CREATED
		apiKeyCreationResponseDTO.apiKeyDTO == null
	}

	void "creates response with API key"() {
		given:
		ApiKeyDTO apiKeyDTO = Mock()

		when:
		ApiKeyCreationResponseDTO apiKeyCreationResponseDTO = apiKeyCreationResponseDTOFactory.createWithApiKeyDTO(apiKeyDTO)

		then:
		apiKeyCreationResponseDTO.created
		apiKeyCreationResponseDTO.failReason == null
		apiKeyCreationResponseDTO.apiKeyDTO == apiKeyDTO
	}

}

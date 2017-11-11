package com.cezarykluczynski.stapi.auth.api_key.operation.block

import spock.lang.Specification

class ApiKeyBlockRelatedResponseDTOFactoryTest extends Specification {

	private ApiKeyBlockRelatedResponseDTOFactory apiKeyRemovalResponseDTOFactory

	void setup() {
		apiKeyRemovalResponseDTOFactory = new ApiKeyBlockRelatedResponseDTOFactory()
	}

	void "creates failed response"() {
		when:
		ApiKeyBlockRelatedResponseDTO apiKeyRemovalResponseDTO = apiKeyRemovalResponseDTOFactory
				.createFailedWithReason(ApiKeyBlockRelatedResponseDTO.FailReason.KEY_DOES_NOT_EXIST)

		then:
		!apiKeyRemovalResponseDTO.successful
		apiKeyRemovalResponseDTO.failReason == ApiKeyBlockRelatedResponseDTO.FailReason.KEY_DOES_NOT_EXIST
	}

	void "creates successful response"() {
		when:
		ApiKeyBlockRelatedResponseDTO apiKeyRemovalResponseDTO = apiKeyRemovalResponseDTOFactory.createSuccessful()

		then:
		apiKeyRemovalResponseDTO.successful
		apiKeyRemovalResponseDTO.failReason == null
	}

}

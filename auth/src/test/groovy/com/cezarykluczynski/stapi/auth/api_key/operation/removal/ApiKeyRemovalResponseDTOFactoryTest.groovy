package com.cezarykluczynski.stapi.auth.api_key.operation.removal

import spock.lang.Specification

class ApiKeyRemovalResponseDTOFactoryTest extends Specification {

	private ApiKeyRemovalResponseDTOFactory apiKeyRemovalResponseDTOFactory

	void setup() {
		apiKeyRemovalResponseDTOFactory = new ApiKeyRemovalResponseDTOFactory()
	}

	void "creates failed response"() {
		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = apiKeyRemovalResponseDTOFactory
				.createFailedWithReason(ApiKeyRemovalResponseDTO.FailReason.KEY_DOES_NOT_EXIST)

		then:
		!apiKeyRemovalResponseDTO.removed
		apiKeyRemovalResponseDTO.failReason == ApiKeyRemovalResponseDTO.FailReason.KEY_DOES_NOT_EXIST
	}

	void "creates successful response"() {
		when:
		ApiKeyRemovalResponseDTO apiKeyRemovalResponseDTO = apiKeyRemovalResponseDTOFactory.createSuccessful()

		then:
		apiKeyRemovalResponseDTO.removed
		apiKeyRemovalResponseDTO.failReason == null
	}

}

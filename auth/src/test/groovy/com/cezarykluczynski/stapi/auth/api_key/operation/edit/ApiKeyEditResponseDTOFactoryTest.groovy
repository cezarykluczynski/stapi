package com.cezarykluczynski.stapi.auth.api_key.operation.edit

import spock.lang.Specification

class ApiKeyEditResponseDTOFactoryTest extends Specification {

	private ApiKeyEditResponseDTOFactory apiKeyEditResponseDTOFactory

	void setup() {
		apiKeyEditResponseDTOFactory = new ApiKeyEditResponseDTOFactory()
	}

	void "creates unsuccessful response with reason"() {
		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = apiKeyEditResponseDTOFactory.createUnsuccessful(ApiKeyEditResponseDTO.FailReason.URL_TOO_LONG)

		then:
		!apiKeyEditResponseDTO.successful
		!apiKeyEditResponseDTO.changed
		apiKeyEditResponseDTO.failReason == ApiKeyEditResponseDTO.FailReason.URL_TOO_LONG
	}

	void "creates unchanged response"() {
		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = apiKeyEditResponseDTOFactory.createUnchanged()

		then:
		apiKeyEditResponseDTO.successful
		!apiKeyEditResponseDTO.changed
		apiKeyEditResponseDTO.failReason == null
	}

	void "creates successful response"() {
		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = apiKeyEditResponseDTOFactory.createSuccessful()

		then:
		apiKeyEditResponseDTO.successful
		apiKeyEditResponseDTO.changed
		apiKeyEditResponseDTO.failReason == null
	}

}

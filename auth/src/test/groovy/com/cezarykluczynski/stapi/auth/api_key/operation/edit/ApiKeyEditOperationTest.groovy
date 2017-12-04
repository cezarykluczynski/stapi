package com.cezarykluczynski.stapi.auth.api_key.operation.edit

import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import spock.lang.Specification

class ApiKeyEditOperationTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long API_KEY_ID = 15L
	private static final String CURRENT_URL = 'CURRENT_URL'
	private static final String CURRENT_DESCRIPTION = 'CURRENT_DESCRIPTION'
	private static final String INCOMING_URL = 'INCOMING_URL'
	private static final String INCOMING_DESCRIPTION = 'INCOMING_DESCRIPTION'

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyEditResponseDTOFactory apiKeyEditResponseDTOFactoryMock

	private ApiKeyEditValidator apiKeyEditValidatorMock

	private ApiKeyEditOperation apiKeyEditOperation

	void setup() {
		apiKeyRepositoryMock = Mock()
		apiKeyEditResponseDTOFactoryMock = Mock()
		apiKeyEditValidatorMock = Mock()
		apiKeyEditOperation = new ApiKeyEditOperation(apiKeyRepositoryMock, apiKeyEditResponseDTOFactoryMock, apiKeyEditValidatorMock)
	}

	void "when account ID is null, exception is thrown"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = Mock()

		when:
		apiKeyEditOperation.execute(null, apiKeyDetailsDTO)

		then:
		0 * _
		thrown(NullPointerException)
	}

	void "when ApiKeyDetailsDTO ID is null, exception is thrown"() {
		when:
		apiKeyEditOperation.execute(ACCOUNT_ID, null)

		then:
		0 * _
		thrown(NullPointerException)
	}

	void "when API key ID is null, exception is thrown"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(null, '', '')

		when:
		apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		0 * _
		thrown(NullPointerException)
	}

	void "when API key is not found, exception is thrown"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(API_KEY_ID, '', '')
		ApiKey invalidApiKey = new ApiKey(id: 0L)

		when:
		apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(invalidApiKey)
		0 * _
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == "Cannot find key ${API_KEY_ID}"
	}

	void "when incoming URL and description equals current URL and description, unchanged response is returned"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(API_KEY_ID, CURRENT_URL, CURRENT_DESCRIPTION)
		ApiKey validApiKey = new ApiKey(id: API_KEY_ID, url: CURRENT_URL, description: CURRENT_DESCRIPTION)
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(validApiKey)
		1 * apiKeyEditResponseDTOFactoryMock.createUnchanged() >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
	}

	void "when incoming URL is too long, unsuccessful response is returned"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(API_KEY_ID, INCOMING_URL, CURRENT_DESCRIPTION)
		ApiKey validApiKey = new ApiKey(id: API_KEY_ID, url: CURRENT_URL, description: CURRENT_DESCRIPTION)
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(validApiKey)
		1 * apiKeyEditValidatorMock.isUrlShortEnough(INCOMING_URL) >> false
		1 * apiKeyEditResponseDTOFactoryMock.createUnsuccessful(ApiKeyEditResponseDTO.FailReason.URL_TOO_LONG) >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
	}

	void "when incoming description is too long, unsuccessful response is returned"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(API_KEY_ID, CURRENT_URL, INCOMING_DESCRIPTION)
		ApiKey validApiKey = new ApiKey(id: API_KEY_ID, url: CURRENT_URL, description: CURRENT_DESCRIPTION)
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(validApiKey)
		1 * apiKeyEditValidatorMock.isDescriptionShortEnough(INCOMING_DESCRIPTION) >> false
		1 * apiKeyEditResponseDTOFactoryMock.createUnsuccessful(ApiKeyEditResponseDTO.FailReason.DESCRIPTION_TOO_LONG) >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
	}

	void "when url and description are short enough, API key is update with them, then successful response is returned"() {
		given:
		ApiKeyDetailsDTO apiKeyDetailsDTO = new ApiKeyDetailsDTO(API_KEY_ID, INCOMING_URL, INCOMING_DESCRIPTION)
		ApiKey validApiKey = new ApiKey(id: API_KEY_ID, url: CURRENT_URL, description: CURRENT_DESCRIPTION)
		ApiKeyEditResponseDTO apiKeyEditResponseDTO = Mock()

		when:
		ApiKeyEditResponseDTO apiKeyEditResponseDTOOutput = apiKeyEditOperation.execute(ACCOUNT_ID, apiKeyDetailsDTO)

		then:
		1 * apiKeyRepositoryMock.findAllByAccountId(ACCOUNT_ID) >> Lists.newArrayList(validApiKey)
		1 * apiKeyEditValidatorMock.isUrlShortEnough(INCOMING_URL) >> true
		1 * apiKeyEditValidatorMock.isDescriptionShortEnough(INCOMING_DESCRIPTION) >> true
		1 * apiKeyEditResponseDTOFactoryMock.createSuccessful() >> apiKeyEditResponseDTO
		0 * _
		apiKeyEditResponseDTOOutput == apiKeyEditResponseDTO
		validApiKey.url == INCOMING_URL
		validApiKey.description == INCOMING_DESCRIPTION
	}

}

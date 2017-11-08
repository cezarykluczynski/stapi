package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO
import spock.lang.Specification

class ApiKeyDTOFactoryTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private ApiKeyCreationValidator apiKeyCreationValidatorMock

	private ApiKeyWithThrottleFactory apiKeyWithThrottleFactoryMock

	private ApiKeyWithThrottlePersister apiKeyWithThrottlePersisterMock

	private ApiKeyDTOFactory apiKeyDTOFactory

	void setup() {
		apiKeyCreationValidatorMock = Mock()
		apiKeyWithThrottleFactoryMock = Mock()
		apiKeyWithThrottlePersisterMock = Mock()
		apiKeyDTOFactory = new ApiKeyDTOFactory(apiKeyWithThrottleFactoryMock, apiKeyWithThrottlePersisterMock, apiKeyCreationValidatorMock)
	}

	void "when no other key was created in the process that resulted in too much keys created, successful response is returned"() {
		given:
		ApiKeyWithThrottleDTO apiKeyWithThrottleDTO = Mock()
		ApiKeyDTO apiKeyDTO = Mock()

		when:
		ApiKeyDTO apiKeyDTOOutput = apiKeyDTOFactory.create(ACCOUNT_ID)

		then:
		1 * apiKeyWithThrottleFactoryMock.createForAccount(ACCOUNT_ID) >> apiKeyWithThrottleDTO
		1 * apiKeyWithThrottlePersisterMock.persistAndGetApiKeyDTO(apiKeyWithThrottleDTO) >> apiKeyDTO
		1 * apiKeyCreationValidatorMock.haveAllowedNumberOfKeys(ACCOUNT_ID) >> true
		0 * _
		apiKeyDTOOutput == apiKeyDTO
	}

	void "when other key was created in the process that resulted in too much keys created, exception is thrown"() {
		given:
		ApiKeyWithThrottleDTO apiKeyWithThrottleDTO = Mock()
		ApiKeyDTO apiKeyDTO = Mock()

		when:
		apiKeyDTOFactory.create(ACCOUNT_ID)

		then:
		1 * apiKeyWithThrottleFactoryMock.createForAccount(ACCOUNT_ID) >> apiKeyWithThrottleDTO
		1 * apiKeyWithThrottlePersisterMock.persistAndGetApiKeyDTO(apiKeyWithThrottleDTO) >> apiKeyDTO
		1 * apiKeyCreationValidatorMock.haveAllowedNumberOfKeys(ACCOUNT_ID) >> false
		0 * _
		thrown(MultipleApiKeysSimultaneouslyCreatedException)
	}

}

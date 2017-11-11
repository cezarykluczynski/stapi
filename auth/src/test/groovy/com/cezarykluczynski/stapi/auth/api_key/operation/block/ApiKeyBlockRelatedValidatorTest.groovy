package com.cezarykluczynski.stapi.auth.api_key.operation.block

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyDoesNotExistException
import com.cezarykluczynski.stapi.auth.api_key.operation.common.KeyNotOwnedByAccountException
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import spock.lang.Specification

class ApiKeyBlockRelatedValidatorTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long DIFFERENT_ACCOUNT_ID = 11L
	private static final Long API_KEY_ID = 15L

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyBlockRelatedValidator apiKeyRemovalValidator

	void setup() {
		apiKeyRepositoryMock = Mock()
		apiKeyRemovalValidator = new ApiKeyBlockRelatedValidator(apiKeyRepositoryMock)
	}

	void "when there is no API key, KeyDoesNotExistException is thrown"() {
		when:
		apiKeyRemovalValidator.validate(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRepositoryMock.findOne(API_KEY_ID) >> null
		0 * _
		thrown(KeyDoesNotExistException)
	}

	void "when API key is owned by different user, KeyNotOwnedByAccountException is thrown"() {
		given:
		ApiKey apiKey = new ApiKey(accountId: DIFFERENT_ACCOUNT_ID)

		when:
		apiKeyRemovalValidator.validate(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRepositoryMock.findOne(API_KEY_ID) >> apiKey
		0 * _
		thrown(KeyNotOwnedByAccountException)
	}

	void "validation passes"() {
		given:
		ApiKey apiKey = new ApiKey(accountId: ACCOUNT_ID)

		when:
		apiKeyRemovalValidator.validate(ACCOUNT_ID, API_KEY_ID)

		then:
		1 * apiKeyRepositoryMock.findOne(API_KEY_ID) >> apiKey
		0 * _
		notThrown(ApiKeyException)
	}

}

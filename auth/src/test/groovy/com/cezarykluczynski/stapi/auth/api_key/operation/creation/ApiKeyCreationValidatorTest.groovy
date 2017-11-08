package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository
import spock.lang.Specification

class ApiKeyCreationValidatorTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long LIMIT = 5
	private static final Long LIMIT_MINUS_ONE = LIMIT - 1
	private static final Long LIMIT_PLUS_ONE = LIMIT + 1

	private ApiKeyRepository apiKeyRepositoryMock

	private ApiKeyLimitProvider apiKeyLimitProviderMock

	private ApiKeyCreationValidator apiKeyCreationValidator

	void setup() {
		apiKeyRepositoryMock = Mock()
		apiKeyLimitProviderMock = Mock()
		apiKeyCreationValidator = new ApiKeyCreationValidator(apiKeyRepositoryMock, apiKeyLimitProviderMock)
	}

	void "tells when more keys can be created"() {
		given:
		boolean canBeCreated

		when:
		canBeCreated = apiKeyCreationValidator.canBeCreated(ACCOUNT_ID)

		then:
		1 * apiKeyRepositoryMock.countByAccountId(ACCOUNT_ID) >> LIMIT
		1 * apiKeyLimitProviderMock.provideKeyLimitPerAccount() >> LIMIT
		0 * _
		!canBeCreated

		when:
		canBeCreated = apiKeyCreationValidator.canBeCreated(ACCOUNT_ID)

		then:
		1 * apiKeyRepositoryMock.countByAccountId(ACCOUNT_ID) >> LIMIT_MINUS_ONE
		1 * apiKeyLimitProviderMock.provideKeyLimitPerAccount() >> LIMIT
		0 * _
		canBeCreated
	}

	void "tells when more keys limit is exceeded"() {
		given:
		boolean haveAllowedNumberOfKeys

		when:
		haveAllowedNumberOfKeys = apiKeyCreationValidator.haveAllowedNumberOfKeys(ACCOUNT_ID)

		then:
		1 * apiKeyRepositoryMock.countByAccountId(ACCOUNT_ID) >> LIMIT
		1 * apiKeyLimitProviderMock.provideKeyLimitPerAccount() >> LIMIT
		0 * _
		haveAllowedNumberOfKeys

		when:
		haveAllowedNumberOfKeys = apiKeyCreationValidator.canBeCreated(ACCOUNT_ID)

		then:
		1 * apiKeyRepositoryMock.countByAccountId(ACCOUNT_ID) >> LIMIT_PLUS_ONE
		1 * apiKeyLimitProviderMock.provideKeyLimitPerAccount() >> LIMIT
		0 * _
		!haveAllowedNumberOfKeys
	}

}

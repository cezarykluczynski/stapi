package com.cezarykluczynski.stapi.auth.api_key.operation.read

import spock.lang.Specification

class ApiKeysReadCriteriaMapperTest extends Specification {

	private static final Long ACCOUNT_ID = 10L
	private static final Long GITHUB_ACCOUNT_ID = 11L
	private static final int PAGE_NUMBER = 2
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'
	private static final String API_KEY = 'API_KEY'

	private ApiKeysReadCriteriaMapper apiKeysReadCriteriaMapper

	void setup() {
		apiKeysReadCriteriaMapper = new ApiKeysReadCriteriaMapper()
	}

	void "creates ApiKeysReadCriteria from account ID"() {
		when:
		ApiKeysReadCriteria apiKeysReadCriteria = apiKeysReadCriteriaMapper.fromAccountId(ACCOUNT_ID)

		then:
		apiKeysReadCriteria.accountId == ACCOUNT_ID
		apiKeysReadCriteria.pageNumber == 0
		apiKeysReadCriteria.gitHubAccountId == null
		apiKeysReadCriteria.apiKeyId == null
		apiKeysReadCriteria.name == null
		apiKeysReadCriteria.email == null
		apiKeysReadCriteria.apiKey == null
	}

	void "creates ApiKeysReadCriteria from ApiKeysSearchCriteriaDTO"() {
		given:
		ApiKeysSearchCriteriaDTO apiKeysSearchCriteriaDTO = new ApiKeysSearchCriteriaDTO(
				pageNumber: PAGE_NUMBER,
				accountId: ACCOUNT_ID,
				gitHubAccountId: GITHUB_ACCOUNT_ID,
				name: NAME,
				email: EMAIL,
				apiKey: API_KEY)

		when:
		ApiKeysReadCriteria apiKeysReadCriteria = apiKeysReadCriteriaMapper.fromSearchCriteria(apiKeysSearchCriteriaDTO)

		then:
		apiKeysReadCriteria.accountId == ACCOUNT_ID
		apiKeysReadCriteria.gitHubAccountId == GITHUB_ACCOUNT_ID
		apiKeysReadCriteria.apiKeyId == null
		apiKeysReadCriteria.pageNumber == PAGE_NUMBER
		apiKeysReadCriteria.name == NAME
		apiKeysReadCriteria.email == EMAIL
		apiKeysReadCriteria.apiKey == API_KEY
	}

}

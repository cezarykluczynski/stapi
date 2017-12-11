package com.cezarykluczynski.stapi.auth.account.operation.read

import spock.lang.Specification

class AccountsReadCriteriaMapperTest extends Specification {

	private static final Long ID = 10L
	private static final Long GITHUB_ACCOUNT_ID = 11L
	private static final int PAGE_NUMBER = 2
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

	private AccountsReadCriteriaMapper accountsReadCriteriaMapper

	void setup() {
		accountsReadCriteriaMapper = new AccountsReadCriteriaMapper()
	}

	void "creates AccountsReadCriteria from AccountsSearchCriteriaDTO"() {
		given:
		AccountsSearchCriteriaDTO accountsSearchCriteriaDTO = new AccountsSearchCriteriaDTO(
				id: ID,
				gitHubAccountId: GITHUB_ACCOUNT_ID,
				pageNumber: PAGE_NUMBER,
				name: NAME,
				email: EMAIL)

		when:
		AccountsReadCriteria accountsReadCriteria = accountsReadCriteriaMapper.fromSearchCriteria(accountsSearchCriteriaDTO)

		then:
		accountsReadCriteria.id == ID
		accountsReadCriteria.gitHubAccountId == GITHUB_ACCOUNT_ID
		accountsReadCriteria.pageNumber == PAGE_NUMBER
		accountsReadCriteria.name == NAME
		accountsReadCriteria.email == EMAIL
	}

}

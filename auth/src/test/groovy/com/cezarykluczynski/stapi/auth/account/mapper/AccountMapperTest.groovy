package com.cezarykluczynski.stapi.auth.account.mapper

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class AccountMapperTest extends Specification {

	private static final Long ID = 5L
	private static final Long GITHUB_ACCOUNT_ID = 11L
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

	private AccountMapper accountMapper

	void setup() {
		accountMapper = new AccountMapper()
	}

	void "throws exception when null is passed"() {
		when:
		accountMapper.map(null)

		then:
		thrown(StapiRuntimeException)
	}

	void "maps Account with account ID to AccountDTO"() {
		when:
		AccountDTO accountDTO = accountMapper.map(new Account(
				id: ID,
				gitHubUserId: GITHUB_ACCOUNT_ID,
				name: NAME,
				email: EMAIL))

		then:
		accountDTO.id == ID
		accountDTO.gitHubAccountId == GITHUB_ACCOUNT_ID
		accountDTO.name == NAME
		accountDTO.email == EMAIL
	}

}

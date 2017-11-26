package com.cezarykluczynski.stapi.model.account.repository

import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.entity.Account_
import com.cezarykluczynski.stapi.model.account.query.AccountQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import org.assertj.core.util.Lists
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class AccountRepositoryImplTest extends Specification {

	private static final Long ACCOUNT_ID = 10L

	private AccountQueryBuilderFactory accountQueryBuilderFactoryMock

	private AccountRepositoryImpl accountRepositoryImpl

	void setup() {
		accountQueryBuilderFactoryMock = Mock()
		accountRepositoryImpl = new AccountRepositoryImpl(accountQueryBuilderFactoryMock)
	}

	void "finds account with consents by ID"() {
		given:
		Account account = Mock()
		QueryBuilder<Account> accountQueryBuilder = Mock()

		when:
		Optional<Account> accountOptional = accountRepositoryImpl.findOneWithConsents(ACCOUNT_ID)

		then:
		1 * accountQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == Integer.MAX_VALUE
			accountQueryBuilder
		}
		1 * accountQueryBuilder.fetch(Account_.consents)
		1 * accountQueryBuilder.equal(Account_.id, ACCOUNT_ID)
		1 * accountQueryBuilder.findAll() >> Lists.newArrayList(account)
		0 * _
		accountOptional.isPresent()
		accountOptional.get() == account
	}

	void "returns empty optional when no account can be found"() {
		given:
		QueryBuilder<Account> accountQueryBuilder = Mock()

		when:
		Optional<Account> accountOptional = accountRepositoryImpl.findOneWithConsents(ACCOUNT_ID)

		then:
		1 * accountQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == 0
			assert pageable.pageSize == Integer.MAX_VALUE
			accountQueryBuilder
		}
		1 * accountQueryBuilder.fetch(Account_.consents)
		1 * accountQueryBuilder.equal(Account_.id, ACCOUNT_ID)
		1 * accountQueryBuilder.findAll() >> Lists.newArrayList()
		0 * _
		!accountOptional.isPresent()
	}

}

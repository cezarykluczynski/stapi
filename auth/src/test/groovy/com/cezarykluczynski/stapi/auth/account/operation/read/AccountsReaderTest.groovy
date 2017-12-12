package com.cezarykluczynski.stapi.auth.account.operation.read

import com.cezarykluczynski.stapi.auth.common.factory.RequestSortDTOFactory
import com.cezarykluczynski.stapi.auth.configuration.AccountProperties
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.entity.Account_
import com.cezarykluczynski.stapi.model.account.query.AccountQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class AccountsReaderTest extends Specification {

	private static final Long ID = 10L
	private static final Long GITHUB_ACCOUNT_ID = 11L
	private static final int PAGE_NUMBER = 2
	private static final int ADMIN_PAGE_SIZE = 20
	private static final String NAME = 'NAME'
	private static final String EMAIL = 'EMAIL'

	private AccountQueryBuilderFactory accountQueryBuilderFactoryMock

	private AccountProperties accountPropertiesMock

	private RequestSortDTOFactory requestSortDTOFactoryMock

	private AccountsReader accountsReader

	void setup() {
		accountQueryBuilderFactoryMock = Mock()
		accountPropertiesMock = new AccountProperties(adminPageSize: ADMIN_PAGE_SIZE)
		requestSortDTOFactoryMock = Mock()
		accountsReader = new AccountsReader(accountQueryBuilderFactoryMock, accountPropertiesMock, requestSortDTOFactoryMock)
	}

	void "finds page using criteria"() {
		given:
		QueryBuilder<Account> accountQueryBuilder = Mock()
		AccountsReadCriteria accountsReadCriteria = new AccountsReadCriteria(
				id: ID,
				gitHubAccountId: GITHUB_ACCOUNT_ID,
				pageNumber: PAGE_NUMBER,
				name: NAME,
				email: EMAIL)
		Page<Account> page = Mock()
		RequestSortDTO requestSortDTO = Mock()

		when:
		Page<Account> pageOutput = accountsReader.execute(accountsReadCriteria)

		then:
		1 * accountQueryBuilderFactoryMock.createQueryBuilder(_ as Pageable) >> { Pageable pageable ->
			assert pageable.pageNumber == PAGE_NUMBER
			assert pageable.pageSize == ADMIN_PAGE_SIZE
			accountQueryBuilder
		}
		1 * accountQueryBuilder.equal(Account_.id, ID)
		1 * accountQueryBuilder.equal(Account_.gitHubUserId, GITHUB_ACCOUNT_ID)
		1 * accountQueryBuilder.like(Account_.name, NAME)
		1 * accountQueryBuilder.like(Account_.email, EMAIL)
		1 * requestSortDTOFactoryMock.create() >> requestSortDTO
		1 * accountQueryBuilder.setSort(requestSortDTO)
		1 * accountQueryBuilder.findPage() >> page
		0 * _
		pageOutput == page
	}

}

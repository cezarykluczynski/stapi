package com.cezarykluczynski.stapi.auth.account.operation.read

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO
import com.cezarykluczynski.stapi.auth.account.mapper.AccountMapper
import com.cezarykluczynski.stapi.auth.api_key.mapper.PagerMapper
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.util.wrapper.Pager
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import spock.lang.Specification

class AccountsReadPageOperationTest extends Specification {

	private AccountsReadCriteriaMapper accountsReadCriteriaMapperMock

	private AccountsReader accountsReaderMock

	private AccountMapper accountMapperMock

	private PagerMapper pageMapperMock

	private AccountReadResponseDTOFactory accountReadResponseDTOFactoryMock

	private AccountsReadPageOperation accountsReadPageOperation

	void setup() {
		accountsReadCriteriaMapperMock = Mock()
		accountsReaderMock = Mock()
		accountMapperMock = Mock()
		pageMapperMock = Mock()
		accountReadResponseDTOFactoryMock = Mock()
		accountsReadPageOperation = new AccountsReadPageOperation(accountsReadCriteriaMapperMock, accountsReaderMock, accountMapperMock, pageMapperMock,
				accountReadResponseDTOFactoryMock)
	}

	void "successful response is returned"() {
		given:
		AccountsSearchCriteriaDTO accountsSearchCriteriaDTO = Mock()
		AccountsReadCriteria accountsReadCriteria = Mock()
		Account account = Mock()
		List<Account> accounts = Lists.newArrayList(account)
		Page<Account> accountPage = new PageImpl<>(accounts)
		Pager pager = Mock()
		AccountDTO accountDTO = Mock()
		AccountReadResponseDTO accountReadResponseDTO = Mock()

		when:
		AccountReadResponseDTO accountReadResponseDTOOutput = accountsReadPageOperation.execute(accountsSearchCriteriaDTO)

		then:
		1 * accountsReadCriteriaMapperMock.fromSearchCriteria(accountsSearchCriteriaDTO) >> accountsReadCriteria
		1 * accountsReaderMock.execute(accountsReadCriteria) >> accountPage
		1 * accountMapperMock.map(account) >> accountDTO
		1 * pageMapperMock.map(accountPage) >> pager
		1 * accountReadResponseDTOFactoryMock.createWithAccountsAndPager(Lists.newArrayList(accountDTO), pager) >> accountReadResponseDTO
		0 * _
		accountReadResponseDTOOutput == accountReadResponseDTO
	}

}

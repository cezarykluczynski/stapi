package com.cezarykluczynski.stapi.auth.account.operation.read;

import com.cezarykluczynski.stapi.auth.account.dto.AccountDTO;
import com.cezarykluczynski.stapi.auth.account.mapper.AccountMapper;
import com.cezarykluczynski.stapi.auth.api_key.mapper.PagerMapper;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountsReadPageOperation {

	private final AccountsReadCriteriaMapper accountsReadCriteriaMapper;

	private final AccountsReader accountsReader;

	private final AccountMapper accountMapper;

	private final PagerMapper pageMapper;

	private final AccountReadResponseDTOFactory accountReadResponseDTOFactory;

	public AccountsReadPageOperation(AccountsReadCriteriaMapper accountsReadCriteriaMapper, AccountsReader accountsReader,
			AccountMapper accountMapper, PagerMapper pageMapper, AccountReadResponseDTOFactory accountReadResponseDTOFactory) {
		this.accountsReadCriteriaMapper = accountsReadCriteriaMapper;
		this.accountsReader = accountsReader;
		this.accountMapper = accountMapper;
		this.pageMapper = pageMapper;
		this.accountReadResponseDTOFactory = accountReadResponseDTOFactory;
	}

	public AccountReadResponseDTO execute(AccountsSearchCriteriaDTO accountsSearchCriteriaDTO) {
		Page<Account> accountPage = accountsReader.execute(accountsReadCriteriaMapper.fromSearchCriteria(accountsSearchCriteriaDTO));
		List<AccountDTO> accountList = accountPage.getContent().stream()
				.map(accountMapper::map)
				.collect(Collectors.toList());
		Pager pager = pageMapper.map(accountPage);
		return accountReadResponseDTOFactory.createWithAccountsAndPager(accountList, pager);
	}

}

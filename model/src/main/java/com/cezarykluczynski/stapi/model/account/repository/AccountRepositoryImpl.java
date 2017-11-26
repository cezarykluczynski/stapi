package com.cezarykluczynski.stapi.model.account.repository;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.account.entity.Account_;
import com.cezarykluczynski.stapi.model.account.query.AccountQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountRepositoryImpl implements AccountRepositoryCustom {

	private final AccountQueryBuilderFactory accountQueryBuilderFactory;

	public AccountRepositoryImpl(AccountQueryBuilderFactory accountQueryBuilderFactory) {
		this.accountQueryBuilderFactory = accountQueryBuilderFactory;
	}

	@Override
	public Optional<Account> findOneWithConsents(Long accountId) {
		QueryBuilder<Account> accountQueryBuilder = accountQueryBuilderFactory.createQueryBuilder(new PageRequest(0, Integer.MAX_VALUE));
		accountQueryBuilder.fetch(Account_.consents);
		accountQueryBuilder.equal(Account_.id, accountId);
		List<Account> accountList = accountQueryBuilder.findAll();
		return accountList.size() == 1 ? Optional.of(accountList.get(0)) : Optional.empty();
	}

}

package com.cezarykluczynski.stapi.auth.account.operation.read;


import com.cezarykluczynski.stapi.auth.common.factory.RequestSortDTOFactory;
import com.cezarykluczynski.stapi.auth.configuration.AccountProperties;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.account.entity.Account_;
import com.cezarykluczynski.stapi.model.account.query.AccountQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.google.common.base.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class AccountsReader {

	private final AccountQueryBuilderFactory accountQueryBuilderFactory;

	private final AccountProperties accountProperties;

	private final RequestSortDTOFactory requestSortDTOFactory;

	AccountsReader(AccountQueryBuilderFactory accountQueryBuilderFactory, AccountProperties accountProperties,
			RequestSortDTOFactory requestSortDTOFactory) {
		this.accountQueryBuilderFactory = accountQueryBuilderFactory;
		this.accountProperties = accountProperties;
		this.requestSortDTOFactory = requestSortDTOFactory;
	}

	public Page<Account> execute(AccountsReadCriteria criteria) {
		int pageNumber = Preconditions.checkNotNull(criteria.getPageNumber(), "Page number cannot be null");
		Pageable pageable = new PageRequest(pageNumber, accountProperties.getAdminPageSize());

		QueryBuilder<Account> accountQueryBuilder = accountQueryBuilderFactory.createQueryBuilder(pageable);
		accountQueryBuilder.equal(Account_.id, criteria.getId());
		accountQueryBuilder.equal(Account_.gitHubUserId, criteria.getGitHubAccountId());
		accountQueryBuilder.like(Account_.name, criteria.getName());
		accountQueryBuilder.like(Account_.email, criteria.getEmail());
		accountQueryBuilder.setSort(requestSortDTOFactory.create());
		return accountQueryBuilder.findPage();
	}

}

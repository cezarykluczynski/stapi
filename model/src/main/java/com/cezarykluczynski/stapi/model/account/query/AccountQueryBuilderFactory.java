package com.cezarykluczynski.stapi.model.account.query;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.common.cache.NoCacheCachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryBuilderFactory extends AbstractQueryBuilderFactory<Account> {

	public AccountQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, new NoCacheCachingStrategy(), Account.class);
	}

}

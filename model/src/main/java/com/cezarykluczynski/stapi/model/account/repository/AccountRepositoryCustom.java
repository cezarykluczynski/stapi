package com.cezarykluczynski.stapi.model.account.repository;

import com.cezarykluczynski.stapi.model.account.entity.Account;

import java.util.Optional;

public interface AccountRepositoryCustom {

	Optional<Account> findOneWithConsents(Long accountId);

}

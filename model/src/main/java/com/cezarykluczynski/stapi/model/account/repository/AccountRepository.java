package com.cezarykluczynski.stapi.model.account.repository;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

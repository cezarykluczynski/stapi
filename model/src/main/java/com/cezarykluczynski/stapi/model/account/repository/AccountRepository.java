package com.cezarykluczynski.stapi.model.account.repository;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

	Optional<Account> findByGitHubUserId(Long gitHubUserId);

}

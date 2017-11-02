package com.cezarykluczynski.stapi.auth.account.api;

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountApi {

	private final AccountRepository accountRepository;

	public AccountApi(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void ensureExists(GitHubUserDetailsDTO gitHubUserDetailsDTO) {
		Long id = gitHubUserDetailsDTO.getId();
		Optional<Account> accountOptional = accountRepository.findByGitHubUserId(id);
		if (!accountOptional.isPresent()) {
			Account account = new Account();
			account.setName(gitHubUserDetailsDTO.getName());
			if (account.getName() == null) {
				account.setName(gitHubUserDetailsDTO.getLogin());
			}
			account.setEmail(gitHubUserDetailsDTO.getEmail());
			account.setGitHubUserId(id);
			try {
				accountRepository.save(account);
			} catch (DataIntegrityViolationException e) {
				// an unlikely event of other thread already creating this user, just carry on
			}
		}
	}

}

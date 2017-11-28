package com.cezarykluczynski.stapi.auth.account.api;

import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor;
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO;
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder;
import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@SuppressWarnings({"MemberName", "ParameterName", "OverloadMethodsDeclarationOrder"})
public class AccountApi {

	private final AccountRepository accountRepository;

	private final ConsentRepository consentRepository;

	private final OAuthSessionHolder oAuthSessionHolder;

	private final AccountConsentTypesExtractor accountConsentTypesExtractor;

	public AccountApi(AccountRepository accountRepository, ConsentRepository consentRepository, OAuthSessionHolder oAuthSessionHolder,
			AccountConsentTypesExtractor accountConsentTypesExtractor) {
		this.accountRepository = accountRepository;
		this.consentRepository = consentRepository;
		this.oAuthSessionHolder = oAuthSessionHolder;
		this.accountConsentTypesExtractor = accountConsentTypesExtractor;
	}

	public Account ensureExists(GitHubUserDetailsDTO gitHubUserDetailsDTO) {
		return ensureExists(gitHubUserDetailsDTO, 0);
	}

	public Optional<Account> findByGitHubUserId(Long gitHubId) {
		return accountRepository.findByGitHubUserId(gitHubId);
	}

	public Optional<Account> findById(Long accountId) {
		return Optional.ofNullable(accountRepository.findOne(accountId));
	}

	public Optional<Account> findByIdWithConsents(Long accountId) {
		return accountRepository.findOneWithConsents(accountId);
	}

	@Transactional
	public void updateConsents(Long accountId, Set<ConsentType> consentsTypes) {
		Account account = accountRepository.findOneWithConsents(accountId).orElseThrow(() -> new StapiRuntimeException("Account not found!"));
		List<Consent> consents = consentRepository.findAll();

		account.getConsents().clear();
		account.getConsents().addAll(accountConsentTypesExtractor.filterByType(consents, consentsTypes));
	}

	@Transactional
	public void remove(Long accountId) {
		accountRepository.delete(accountId);
		oAuthSessionHolder.remove();
	}

	private Account ensureExists(GitHubUserDetailsDTO gitHubUserDetailsDTO, int depth) {
		if (depth == 2) {
			throw new StapiRuntimeException("Cannot ensure user is saved locally!");
		}

		Optional<Account> accountOptional = accountRepository.findByGitHubUserId(gitHubUserDetailsDTO.getId());
		return accountOptional.orElseGet(() -> createAccount(gitHubUserDetailsDTO, depth));
	}

	private Account createAccount(GitHubUserDetailsDTO gitHubUserDetailsDTO, int depth) {
		Account account = new Account();
		account.setName(gitHubUserDetailsDTO.getName());
		if (account.getName() == null) {
			account.setName(gitHubUserDetailsDTO.getLogin());
		}
		account.setEmail(gitHubUserDetailsDTO.getEmail());
		account.setGitHubUserId(gitHubUserDetailsDTO.getId());
		try {
			return accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {
			// in an unlikely event of other thread already creating this user, another run should do it
			return ensureExists(gitHubUserDetailsDTO, depth + 1);
		}
	}

	public Optional<Account> findOneWithConsents(Long accountId) {
		return accountRepository.findOneWithConsents(accountId);
	}
}

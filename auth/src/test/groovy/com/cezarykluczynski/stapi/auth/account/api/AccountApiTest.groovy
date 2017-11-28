package com.cezarykluczynski.stapi.auth.account.api

import com.cezarykluczynski.stapi.auth.account.operation.edit.AccountConsentTypesExtractor
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import com.cezarykluczynski.stapi.auth.oauth.session.OAuthSessionHolder
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository
import com.cezarykluczynski.stapi.model.consent.entity.Consent
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class AccountApiTest extends Specification {

	private static final Long ID = 11L
	private static final Long ACCOUNT_ID = 10L
	private static final Long GITHUB_ID = 12L
	private static final String NAME = 'NAME'
	private static final String LOGIN = 'LOGIN'

	private AccountRepository accountRepositoryMock

	private ConsentRepository consentRepositoryMock

	private OAuthSessionHolder oAuthSessionHolderMock

	private AccountConsentTypesExtractor accountConsentTypesExtractorMock

	private AccountApi accountApi

	void setup() {
		accountRepositoryMock = Mock()
		consentRepositoryMock = Mock()
		oAuthSessionHolderMock = Mock()
		accountConsentTypesExtractorMock = Mock()
		accountApi = new AccountApi(accountRepositoryMock, consentRepositoryMock, oAuthSessionHolderMock, accountConsentTypesExtractorMock)
	}

	void "when account is found by GitHub ID, nothing happens"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = Mock()
		Account account = Mock()

		when:
		Account accountOutput = accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * gitHubUserDetailsDTO.id >> ID
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.of(account)
		0 * _
		accountOutput == account
	}

	void "when account is not found, it is created with only login"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN)

		when:
		Account accountOutput = accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		1 * accountRepositoryMock.save(_ as Account) >> { Account account ->
			assert account.name == LOGIN
			assert account.gitHubUserId == ID
			account
		}
		0 * _
		accountOutput.name == LOGIN
		accountOutput.gitHubUserId == ID
	}

	void "when account is not found, it is created with name"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN, name: NAME)

		when:
		Account accountOutput = accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		1 * accountRepositoryMock.save(_ as Account) >> { Account account ->
			assert account.name == NAME
			assert account.gitHubUserId == ID
			account
		}
		0 * _
		accountOutput.name == NAME
		accountOutput.gitHubUserId == ID
	}

	void "single DataIntegrityViolationException is tolerated while saving"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, name: NAME)
		Account account = Mock()

		when:
		Account accountOutput = accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		1 * accountRepositoryMock.save(_ as Account) >> { Account accountInput ->
			throw new DataIntegrityViolationException('')
		}
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.of(account)
		0 * _
		notThrown(Exception)
		accountOutput == account
	}

	void "two DataIntegrityViolationExceptions are turned into StapiRuntimeException"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, name: NAME)

		when:
		accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		1 * accountRepositoryMock.save(_ as Account) >> { Account account ->
			throw new DataIntegrityViolationException('')
		}

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		1 * accountRepositoryMock.save(_ as Account) >> { Account account ->
			throw new DataIntegrityViolationException('')
		}

		then:
		0 * _
		thrown(StapiRuntimeException)
	}

	void "deletes account and session entry"() {
		when:
		accountApi.remove(ACCOUNT_ID)

		then:
		1 * accountRepositoryMock.delete(ACCOUNT_ID)
		1 * oAuthSessionHolderMock.remove()
		0 * _
	}

	void "finds account by GitHub ID"() {
		given:
		Account account = Mock()

		when:
		Optional<Account> accountOptional = accountApi.findByGitHubUserId(GITHUB_ID)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(GITHUB_ID) >> Optional.of(account)
		0 * _
		accountOptional.isPresent()
		accountOptional.get() == account
	}

	void "find account by ID"() {
		given:
		Account account = Mock()

		when:
		Optional<Account> accountOptional = accountApi.findById(ID)

		then:
		1 * accountRepositoryMock.findOne(ID) >> account
		0 * _
		accountOptional.isPresent()
		accountOptional.get() == account
	}

	void "find account by ID, with consents"() {
		given:
		Account account = Mock()

		when:
		Optional<Account> accountOptional = accountApi.findByIdWithConsents(ID)

		then:
		1 * accountRepositoryMock.findOneWithConsents(ID) >> Optional.of(account)
		0 * _
		accountOptional.isPresent()
		accountOptional.get() == account
	}

	void "updates consents"() {
		given:
		ConsentType consentType = ConsentType.TECHNICAL_MAILING
		Account account = new Account()
		Consent consent = Mock()

		when:
		accountApi.updateConsents(ACCOUNT_ID, Sets.newHashSet(consentType))

		then:
		1 * accountRepositoryMock.findOneWithConsents(ACCOUNT_ID) >> Optional.of(account)
		1 * consentRepositoryMock.findAll() >> Lists.newArrayList(consent)
		1 * accountConsentTypesExtractorMock.filterByType(Lists.newArrayList(consent), Sets.newHashSet(consentType)) >> Sets.newHashSet(consent)
		0 * _
		account.consents.contains consent
	}

	void "does not update consents when account could not be found"() {
		given:
		ConsentType consentType = ConsentType.TECHNICAL_MAILING

		when:
		accountApi.updateConsents(ACCOUNT_ID, Sets.newHashSet(consentType))

		then:
		1 * accountRepositoryMock.findOneWithConsents(ACCOUNT_ID) >> Optional.empty()
		0 * _
		thrown(StapiRuntimeException)
	}

	void "finds one account with consents"() {
		given:
		Account account = Mock()

		when:
		Optional<Account> accountOptional = accountApi.findOneWithConsents(ACCOUNT_ID)

		then:
		1 * accountRepositoryMock.findOneWithConsents(ACCOUNT_ID) >> Optional.of(account)
		0 * _
		accountOptional.isPresent()
		accountOptional.get() == account
	}

}

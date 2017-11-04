package com.cezarykluczynski.stapi.auth.oauth.account.api

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class AccountApiTest extends Specification {

	private static final Long ID = 11L
	private static final String NAME = 'NAME'
	private static final String LOGIN = 'LOGIN'

	private AccountRepository accountRepositoryMock

	private AccountApi accountApi

	void setup() {
		accountRepositoryMock = Mock()
		accountApi = new AccountApi(accountRepositoryMock)
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

}

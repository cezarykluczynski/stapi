package com.cezarykluczynski.stapi.auth.oauth.account.api

import com.cezarykluczynski.stapi.auth.account.api.AccountApi
import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import com.cezarykluczynski.stapi.model.account.entity.Account
import com.cezarykluczynski.stapi.model.account.repository.AccountRepository
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
		accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * gitHubUserDetailsDTO.id >> ID
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.of(account)
		0 * _
	}

	void "when account is not found, it is created with only login"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN)

		when:
		accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		0 * _
		accountRepositoryMock.save(_ as Account) >> { Account account ->
			assert account.name == LOGIN
			assert account.gitHubUserId == ID
			account
		}
	}

	void "when account is not found, it is created with name"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, login: LOGIN, name: NAME)

		when:
		accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		0 * _
		accountRepositoryMock.save(_ as Account) >> { Account account ->
			assert account.name == NAME
			assert account.gitHubUserId == ID
			account
		}
	}

	void "DataIntegrityViolationException is tolerated while saving"() {
		given:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO(id: ID, name: NAME)

		when:
		accountApi.ensureExists(gitHubUserDetailsDTO)

		then:
		1 * accountRepositoryMock.findByGitHubUserId(ID) >> Optional.empty()
		0 * _
		accountRepositoryMock.save(_ as Account) >> { Account account ->
			throw new DataIntegrityViolationException('')
		}
		notThrown(Exception)
	}

}

package com.cezarykluczynski.stapi.auth.oauth.github.service

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO
import spock.lang.Specification

class GitHubUserDetailsDTOFactoryTest extends Specification {

	private static final String LOGIN = 'octocat'
	private static final Long ID = 1L
	private static final String NAME = 'monalisa octocat'
	private static final String EMAIL = 'octocat@github.com'
	private static final String JSON_WITHOUT_ID = '{"login": "' + LOGIN + '"}'
	private static final String JSON_WITHOUT_LOGIN = '{"id": ' + ID + '}'
	private static final String JSON_WITH_ID_AND_LOGIN = '{"login": "' + LOGIN + '", "id": ' + ID + '}'
	private static final String JSON_WITH_NULL_NAME_AND_EMAIL = '{"login": "octocat", "id": 1, "name": null, "email": null}'
	private static final String JSON_WITH_ALL_DATA = '{"login": "octocat", "id": 1, "name": "' + NAME + '", "email": "' + EMAIL + '"}'

	private GitHubUserDetailsDTOFactory gitHubUserDetailsDTOFactory

	void setup() {
		gitHubUserDetailsDTOFactory = new GitHubUserDetailsDTOFactory()
	}

	void "throws exception when id is not present"() {
		when:
		gitHubUserDetailsDTOFactory.create(JSON_WITHOUT_ID)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when login is not present"() {
		when:
		gitHubUserDetailsDTOFactory.create(JSON_WITHOUT_LOGIN)

		then:
		thrown(NullPointerException)
	}

	void "creates GitHubUserDetailsDTO with only id and login"() {
		when:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = gitHubUserDetailsDTOFactory.create(JSON_WITH_ID_AND_LOGIN)

		then:
		gitHubUserDetailsDTO.login == LOGIN
		gitHubUserDetailsDTO.id == ID
		gitHubUserDetailsDTO.name == null
		gitHubUserDetailsDTO.email == null
	}

	void "creates GitHubUserDetailsDTO with null name and e-mail"() {
		when:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = gitHubUserDetailsDTOFactory.create(JSON_WITH_NULL_NAME_AND_EMAIL)

		then:
		gitHubUserDetailsDTO.login == LOGIN
		gitHubUserDetailsDTO.id == ID
		gitHubUserDetailsDTO.name == null
		gitHubUserDetailsDTO.email == null
	}

	void "creates GitHubUserDetailsDTO with all data"() {
		when:
		GitHubUserDetailsDTO gitHubUserDetailsDTO = gitHubUserDetailsDTOFactory.create(JSON_WITH_ALL_DATA)

		then:
		gitHubUserDetailsDTO.login == LOGIN
		gitHubUserDetailsDTO.id == ID
		gitHubUserDetailsDTO.name == NAME
		gitHubUserDetailsDTO.email == EMAIL
	}

}

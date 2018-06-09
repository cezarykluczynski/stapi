package com.cezarykluczynski.stapi.server.github.service

import com.cezarykluczynski.stapi.server.github.model.GitHubDTO
import com.google.common.cache.Cache
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import spock.lang.Specification

class GitHubApiTest extends Specification {

	private static final int STARGAZERS_COUNT = 23

	private GitHub gitHubMock
	private Cache<String, GitHubDTO> cacheMock
	private GitHubApi gitHubApi

	void setup() {
		gitHubMock = Mock()
		cacheMock = Mock()
		gitHubApi = new GitHubApi(gitHubMock, cacheMock)
	}

	void "gets data from GitHub"() {
		given:
		GHRepository ghRepository = Mock()

		when:
		GitHubDTO gitHubDTO = gitHubApi.projectDetails

		then:
		1 * cacheMock.getIfPresent(GitHubApi.KEY) >> null
		1 * gitHubMock.getRepository('cezarykluczynski/stapi') >> ghRepository
		1 * ghRepository.stargazersCount >> STARGAZERS_COUNT
		1 * cacheMock.put(GitHubApi.KEY, _ as GitHubDTO) >> { String key, GitHubDTO gitHubDTOToCache ->
			assert gitHubDTOToCache.stargazersCount == STARGAZERS_COUNT
		}
		0 * _
		gitHubDTO.stargazersCount == STARGAZERS_COUNT

	}

	void "gets empty response when GitHub cannot be queried"() {
		when:
		GitHubDTO gitHubDTO = gitHubApi.projectDetails

		then:
		1 * cacheMock.getIfPresent(GitHubApi.KEY) >> null
		1 * gitHubMock.getRepository('cezarykluczynski/stapi') >> {
			throw new IOException()
		}
		1 * cacheMock.put(GitHubApi.KEY, _ as GitHubDTO) >> { String key, GitHubDTO gitHubDTOToCache ->
			assert gitHubDTOToCache.stargazersCount == null
		}
		0 * _
		gitHubDTO.stargazersCount == null
	}

	void "gets data from cache"() {
		given:
		GitHubDTO gitHubDTO = Mock()

		when:
		GitHubDTO gitHubDTOOutput = gitHubApi.projectDetails

		then:
		1 * cacheMock.getIfPresent(GitHubApi.KEY) >> gitHubDTO
		0 * _
		gitHubDTOOutput == gitHubDTO
	}

}

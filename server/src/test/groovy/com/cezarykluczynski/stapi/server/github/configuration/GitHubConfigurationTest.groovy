package com.cezarykluczynski.stapi.server.github.configuration

import com.cezarykluczynski.stapi.server.github.model.GitHubDTO
import com.google.common.cache.Cache
import org.kohsuke.github.GitHub
import spock.lang.Specification

class GitHubConfigurationTest extends Specification {

	private GitHubConfiguration gitHubConfiguration

	void setup() {
		gitHubConfiguration = new GitHubConfiguration()
	}

	void "created GitHub"() {
		when:
		GitHub gitHub = gitHubConfiguration.gitHub()

		then:
		gitHub != null
	}

	void "creates GitHub cache"() {
		when:
		Cache<String, GitHubDTO> cache = gitHubConfiguration.gitHubCache()

		then:
		cache != null
	}

}

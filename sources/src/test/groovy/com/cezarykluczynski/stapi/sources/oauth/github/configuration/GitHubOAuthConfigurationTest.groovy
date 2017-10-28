package com.cezarykluczynski.stapi.sources.oauth.github.configuration

import com.squareup.okhttp.OkHttpClient
import spock.lang.Specification

class GitHubOAuthConfigurationTest extends Specification {

	private GitHubOAuthConfiguration gitHubOAuthConfiguration

	void setup() {
		gitHubOAuthConfiguration = new GitHubOAuthConfiguration()
	}

	void "OkHttpClient is created"() {
		when:
		OkHttpClient okHttpClient = gitHubOAuthConfiguration.okHttpClient()

		then:
		okHttpClient != null
	}

}

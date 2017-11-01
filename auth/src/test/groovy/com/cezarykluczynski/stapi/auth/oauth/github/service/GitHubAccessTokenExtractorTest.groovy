package com.cezarykluczynski.stapi.auth.oauth.github.service

import spock.lang.Specification
import spock.lang.Unroll

class GitHubAccessTokenExtractorTest extends Specification {

	private GitHubAccessTokenExtractor gitHubAccessTokenExtractor

	void setup() {
		gitHubAccessTokenExtractor = new GitHubAccessTokenExtractor()
	}

	@Unroll('when #input is passed, #output is returned')
	void "when input is passed, output is returned"() {
		expect:
		gitHubAccessTokenExtractor.extract(input) == output

		where:
		input                                                                     | output
		null                                                                      | null
		'token_type=bearer'                                                       | null
		'access_token&token_type=bearer'                                          | null
		'access_token=e72e16c7e42f292c6912e7710c838347ae178b4a'                   | 'e72e16c7e42f292c6912e7710c838347ae178b4a'
		'access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer' | 'e72e16c7e42f292c6912e7710c838347ae178b4a'
	}

}

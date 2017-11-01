package com.cezarykluczynski.stapi.auth.oauth.github.service

import com.cezarykluczynski.stapi.auth.oauth.github.configuration.GitHubOAuthProperties
import com.google.common.collect.Lists
import spock.lang.Specification

class GitHubAdminDetectorTest extends Specification {

	private static final Long VALID_ADMIN_ID = 11
	private static final Long INVALID_ADMIN_ID = 13

	private GitHubOAuthProperties gitHubOAuthPropertiesMock

	private GitHubAdminDetector gitHubAdminDetector

	void setup() {
		gitHubOAuthPropertiesMock = Mock()
		gitHubAdminDetector = new GitHubAdminDetector(gitHubOAuthPropertiesMock)
	}

	void "detects admin ID"() {
		when:
		boolean isAdminId = gitHubAdminDetector.isAdminId(VALID_ADMIN_ID)

		then:
		1 * gitHubOAuthPropertiesMock.adminIdentifiers >> Lists.newArrayList(VALID_ADMIN_ID)
		0 * _
		isAdminId
	}

	void "detects non-admin ID"() {
		when:
		boolean isAdminId = gitHubAdminDetector.isAdminId(INVALID_ADMIN_ID)

		then:
		1 * gitHubOAuthPropertiesMock.adminIdentifiers >> Lists.newArrayList(VALID_ADMIN_ID)
		0 * _
		!isAdminId
	}

}

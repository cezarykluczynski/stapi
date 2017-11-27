package com.cezarykluczynski.stapi.auth.configuration

import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository
import spock.lang.Specification

class AuthConfigurationTest extends Specification {

	private ConsentRepository consentRepositoryMock

	private AuthConfiguration authConfiguration

	void setup() {
		consentRepositoryMock = Mock()
		authConfiguration = new AuthConfiguration(consentRepository: consentRepositoryMock)
	}

	void "ensures consents exists"() {
		when:
		authConfiguration.postContruct()

		then:
		1 * consentRepositoryMock.ensureExists()
		0 * _
	}

}

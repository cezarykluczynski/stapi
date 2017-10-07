package com.cezarykluczynski.stapi.server.common.throttle

import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType
import spock.lang.Specification

class FrequentRequestsKeysFactoryTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'
	private static final String API_KEY = 'API_KEY'

	private FrequentRequestsKeysFactory frequentRequestsKeysFactory

	void setup() {
		frequentRequestsKeysFactory = new FrequentRequestsKeysFactory()
	}

	void "creates set with only IP address, if request credential type is IP address"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)

		when:
		Set<FrequentRequestsKey> frequentRequestsKeySet = frequentRequestsKeysFactory.create(requestCredential)

		then:
		frequentRequestsKeySet.size() == 1
		frequentRequestsKeySet[0].requestCredentialType == RequestCredentialType.IP_ADDRESS
		frequentRequestsKeySet[0].value == IP_ADDRESS
	}

	void "creates set with IP address and API key, if request credential type is API key"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.API_KEY,
				apiKey: API_KEY,
				ipAddress: IP_ADDRESS)

		when:
		Set<FrequentRequestsKey> frequentRequestsKeySet = frequentRequestsKeysFactory.create(requestCredential)

		then:
		frequentRequestsKeySet.size() == 2
		frequentRequestsKeySet[0].requestCredentialType == RequestCredentialType.IP_ADDRESS
		frequentRequestsKeySet[0].value == IP_ADDRESS
		frequentRequestsKeySet[1].requestCredentialType == RequestCredentialType.API_KEY
		frequentRequestsKeySet[1].value == API_KEY
	}

}

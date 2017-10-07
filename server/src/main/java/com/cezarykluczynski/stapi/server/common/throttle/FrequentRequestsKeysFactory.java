package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential;
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class FrequentRequestsKeysFactory {

	Set<FrequentRequestsKey> create(RequestCredential requestCredential) {
		Set<FrequentRequestsKey> frequentRequestsKeySet = Sets.newLinkedHashSet();

		frequentRequestsKeySet.add(FrequentRequestsKey.of(RequestCredentialType.IP_ADDRESS, requestCredential.getIpAddress()));

		if (RequestCredentialType.API_KEY.equals(requestCredential.getRequestCredentialType())) {
			frequentRequestsKeySet.add(FrequentRequestsKey.of(RequestCredentialType.API_KEY, requestCredential.getApiKey()));
		}

		return frequentRequestsKeySet;
	}

}

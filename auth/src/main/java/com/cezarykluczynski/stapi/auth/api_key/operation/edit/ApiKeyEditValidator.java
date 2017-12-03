package com.cezarykluczynski.stapi.auth.api_key.operation.edit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
class ApiKeyEditValidator {

	boolean isUrlShortEnough(String incomingUrl) {
		return incomingUrl == null || StringUtils.length(incomingUrl) <= 256;
	}

	boolean isDescriptionShortEnough(String incomingDescription) {
		return incomingDescription == null || StringUtils.length(incomingDescription) <= 4000;
	}

}

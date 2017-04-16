package com.cezarykluczynski.stapi.server.common.throttle.credential;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
class RestApiKeyExtractor {

	String extract(HttpServletRequest httpServletRequest) {
		String[] apiKeyArray = httpServletRequest.getParameterMap().get(RequestCredential.API_KEY);
		if (apiKeyArray != null && apiKeyArray.length == 1) {
			return apiKeyArray[0];
		}
		return null;
	}

}

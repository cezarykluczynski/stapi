package com.cezarykluczynski.stapi.server.common.throttle.credential;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class RequestCredentialProvider {

	private final HttpServletRequest httpServletRequest;

	@Inject
	public RequestCredentialProvider(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public RequestCredential provideRequestCredential() {
		RequestCredential requestCredential = new RequestCredential();
		requestCredential.setRequestCredentialType(RequestCredentialType.IP_ADDRESS);
		requestCredential.setIpAddress(httpServletRequest.getRemoteAddr());
		return requestCredential;
	}

}

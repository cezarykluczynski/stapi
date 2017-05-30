package com.cezarykluczynski.stapi.server.common.throttle;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
class ThrottleQualifyingService {

	private static final String COMMON_URI_PART = "rest/common";

	private final HttpServletRequest httpServletRequest;

	@Inject
	ThrottleQualifyingService(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public boolean isQualifiedForThrottle() {
		return httpServletRequest.getRequestURI().length() < 19 || !COMMON_URI_PART.equals(httpServletRequest.getRequestURI().substring(8, 19));
	}

}

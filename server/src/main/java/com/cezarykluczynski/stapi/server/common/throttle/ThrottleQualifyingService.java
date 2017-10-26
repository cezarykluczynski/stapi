package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Profile(SpringProfile.API_THROTTLE)
class ThrottleQualifyingService {

	private static final String COMMON_URI_PART = "rest/common";

	private final HttpServletRequest httpServletRequest;

	ThrottleQualifyingService(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public boolean isQualifiedForThrottle() {
		return httpServletRequest.getRequestURI().length() < 19 || !COMMON_URI_PART.equals(httpServletRequest.getRequestURI().substring(8, 19));
	}

}

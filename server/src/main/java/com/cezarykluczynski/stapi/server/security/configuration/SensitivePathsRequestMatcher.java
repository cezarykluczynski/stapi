package com.cezarykluczynski.stapi.server.security.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SensitivePathsRequestMatcher implements RequestMatcher {

	@Override
	public boolean matches(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		return pathInfo != null && StringUtils.contains(pathInfo, SecurityConfiguration.CSFR_SENSITIVE_PATH);
	}

}

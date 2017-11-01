package com.cezarykluczynski.stapi.server.security.filter;

import com.cezarykluczynski.stapi.server.security.configuration.SecurityConfiguration;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class OriginVerifyingFilter implements Filter {

	private static final String ORIGIN_HEADER_NAME = "origin";
	private static final String ERROR_MESSAGE = "Potential CSFR prevented";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String pathInfo = httpServletRequest.getPathInfo();

		if (pathInfo != null && StringUtils.contains(pathInfo, SecurityConfiguration.CSFR_SENSITIVE_PATH)) {
			verifyOrigin(httpServletRequest);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		// do nothing
	}

	private void verifyOrigin(HttpServletRequest httpServletRequest) {
		String applicationHost = httpServletRequest.getServerName();
		String originRaw = httpServletRequest.getHeader(ORIGIN_HEADER_NAME);
		String originHost = extractOriginHost(originRaw);

		if (originHost != null && !StringUtils.equals(applicationHost, originHost)) {
			doThrow();
		}
	}

	private String extractOriginHost(String originRaw) {
		URI uri;
		String originHost = null;
		try {
			if (originRaw != null) {
				uri = new URI(originRaw);
				originHost = uri.getHost();
				if (originHost == null) {
					doThrow();
				}
			}
		} catch (URISyntaxException e) {
			doThrow();
		}
		return originHost;
	}

	private void doThrow() {
		throw new StapiRuntimeException(ERROR_MESSAGE);
	}

}

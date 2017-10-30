package com.cezarykluczynski.stapi.server.security.filter;

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

@Service
public class CsfrFilter implements Filter {

	private static final String[] CSFR_SENSITIVE_PATHS = {"/rest/panel/", "/rest/oauth/"};
	private static final String ORIGIN_HEADER_NAME = "origin";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String pathInfo = httpServletRequest.getPathInfo();

		if (pathInfo != null && StringUtils.containsAny(pathInfo, CSFR_SENSITIVE_PATHS)) {
			String applicationHost = servletRequest.getServerName();
			String originHost = httpServletRequest.getHeader(ORIGIN_HEADER_NAME);
			if (!StringUtils.equals(applicationHost, originHost)) {
				throw new StapiRuntimeException("Potential CSFR prevented");
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		// do nothing
	}

}

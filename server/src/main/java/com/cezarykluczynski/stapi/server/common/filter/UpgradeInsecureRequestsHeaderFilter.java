package com.cezarykluczynski.stapi.server.common.filter;

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable;
import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Priority(2)
public class UpgradeInsecureRequestsHeaderFilter implements Filter {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CanonicalDomainFilter.class);
	private static final String HEADER_NAME = "Upgrade-Insecure-Requests";
	private static final String HTTP_PREFIX = "http://";

	private final Environment environment;

	private boolean upgradeInsecureRequestsEnabled;

	public UpgradeInsecureRequestsHeaderFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		upgradeInsecureRequestsEnabled = "true".equalsIgnoreCase(environment.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS));
		LOG.info("\"{}\" header will be {}.", HEADER_NAME, upgradeInsecureRequestsEnabled ? "respected" : "ignored");
	}

	@SuppressWarnings("ThrowsCount")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (upgradeInsecureRequestsEnabled && servletRequest instanceof HttpServletRequest request) {
			String upgradeInsecureRequestsHeaderValue = request.getHeader(HEADER_NAME);
			if ("1".equals(upgradeInsecureRequestsHeaderValue)) {
				String url = request.getRequestURL().toString();
				if (url.startsWith(HTTP_PREFIX) && servletResponse instanceof HttpServletResponse response) {
					response.setHeader("Location", "https://" + url.substring(HTTP_PREFIX.length()));
					response.setHeader("Vary", HEADER_NAME);
					response.setStatus(302);
					filterChain.doFilter(request, response);
					return;
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

}

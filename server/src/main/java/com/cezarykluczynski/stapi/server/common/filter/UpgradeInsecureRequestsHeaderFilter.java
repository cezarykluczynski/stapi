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

	@SuppressWarnings("ConstantName")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UpgradeInsecureRequestsHeaderFilter.class);
	private static final String UPGRADE_INSECURE_REQUESTS_HEADER_NAME = "Upgrade-Insecure-Requests";
	private static final String FORWARDER_PROTO_HEADER_NAME = "X-Forwarded-Proto";
	private static final String HTTP = "http";

	private final Environment environment;

	private boolean upgradeInsecureRequestsEnabled;

	public UpgradeInsecureRequestsHeaderFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		upgradeInsecureRequestsEnabled = "true".equalsIgnoreCase(environment.getProperty(EnvironmentVariable.STAPI_UPGRADE_INSECURE_REQUESTS));
		log.info("\"{}\" header will be {}.", UPGRADE_INSECURE_REQUESTS_HEADER_NAME, upgradeInsecureRequestsEnabled ? "respected" : "ignored");
	}

	@SuppressWarnings("ThrowsCount")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (upgradeInsecureRequestsEnabled && servletRequest instanceof HttpServletRequest request) {
			String upgradeInsecureRequestsHeaderValue = request.getHeader(UPGRADE_INSECURE_REQUESTS_HEADER_NAME);
			String forwardedProtoHeaderValue = request.getHeader(FORWARDER_PROTO_HEADER_NAME);
			if ("1".equals(upgradeInsecureRequestsHeaderValue) && HTTP.equalsIgnoreCase(forwardedProtoHeaderValue)) {
				if (servletResponse instanceof HttpServletResponse httpServletResponse && HTTP.equals(servletRequest.getScheme())) {
					httpServletResponse.setHeader("Vary", UPGRADE_INSECURE_REQUESTS_HEADER_NAME);
					httpServletResponse.sendRedirect("https://" + request.getRequestURL().toString().substring("http://".length()));
					return;
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

}

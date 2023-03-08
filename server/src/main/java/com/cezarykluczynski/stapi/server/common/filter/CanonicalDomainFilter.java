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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Priority(1)
public class CanonicalDomainFilter implements Filter {

	@SuppressWarnings("ConstantName")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CanonicalDomainFilter.class);

	private final Environment environment;

	private String canonicalDomainToCheck;

	public CanonicalDomainFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		canonicalDomainToCheck = environment.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN);
		if (canonicalDomainToCheck != null) {
			log.info("Setting canonical domain to {}.", canonicalDomainToCheck);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (canonicalDomainToCheck != null) {
			if (request instanceof HttpServletRequest) {
				String url = ((HttpServletRequest) request).getRequestURL().toString();
				if (!url.startsWith("http://" + canonicalDomainToCheck) && !url.startsWith("https://" + canonicalDomainToCheck)) {
					throw new RuntimeException(String.format("Unexpected URL %s, expecting URL in domain %s", url, canonicalDomainToCheck));
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}

}

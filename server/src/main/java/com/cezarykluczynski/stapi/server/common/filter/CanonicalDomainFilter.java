package com.cezarykluczynski.stapi.server.common.filter;

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable;
import org.springframework.core.env.Environment;
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
public class CanonicalDomainFilter implements Filter {

	private final Environment environment;

	private String canonicalDomainToCheck;

	public CanonicalDomainFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		canonicalDomainToCheck = environment.getProperty(EnvironmentVariable.STAPI_CANONICAL_DOMAIN);
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

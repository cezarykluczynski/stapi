package com.cezarykluczynski.stapi.server.common.metrics.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RestEndpointApiBrowserFilter implements Filter {

	private final ThreadLocal<Boolean> apiBrowserRequest = new ThreadLocal<>();

	public boolean isApiBrowser() {
		return Boolean.TRUE.equals(apiBrowserRequest.get());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest httpServletRequest) {
			apiBrowserRequest.set("true".equals(httpServletRequest.getHeader("X-Api-Browser")));
		}
		chain.doFilter(request, response);
	}

}

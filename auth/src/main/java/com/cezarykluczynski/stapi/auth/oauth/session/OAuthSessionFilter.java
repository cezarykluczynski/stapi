package com.cezarykluczynski.stapi.auth.oauth.session;

import com.google.common.collect.Lists;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class OAuthSessionFilter implements Filter {

	private final OAuthSessionHolder oauthSessionHolder;

	public OAuthSessionFilter(OAuthSessionHolder oauthSessionHolder) {
		this.oauthSessionHolder = oauthSessionHolder;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		@SuppressWarnings("LocalVariableName")
		OAuthSession oAuthSession = oauthSessionHolder.getNullableOAuthSession();
		Authentication authentication = new UsernamePasswordAuthenticationToken(oAuthSession, null, createGrantedAuthorities(oAuthSession));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		// do nothing
	}

	private Collection<? extends GrantedAuthority> createGrantedAuthorities(@SuppressWarnings("ParameterName") OAuthSession oAuthSession) {
		if (oAuthSession == null) {
			return Lists.newArrayList();
		}

		return oAuthSession.getPermissions().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
}

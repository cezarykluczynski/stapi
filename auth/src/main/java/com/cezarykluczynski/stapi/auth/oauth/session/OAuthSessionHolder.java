package com.cezarykluczynski.stapi.auth.oauth.session;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OAuthSessionHolder {

	@SuppressWarnings("MemberName")
	private OAuthSession oAuthSession;

	public synchronized OAuthSession getNullableOAuthSession() {
		return oAuthSession == null ? null : oAuthSession.copy();
	}

	public synchronized OAuthSession getOAuthSession() {
		if (oAuthSession == null) {
			throw new StapiRuntimeException("No session!");
		}

		return oAuthSession.copy();
	}

	void setOAuthSession(@SuppressWarnings("ParameterName") OAuthSession newOAuthSession) {
		this.oAuthSession = newOAuthSession;
	}

}

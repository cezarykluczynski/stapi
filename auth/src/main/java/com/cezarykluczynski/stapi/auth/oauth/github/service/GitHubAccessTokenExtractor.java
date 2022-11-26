package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
class GitHubAccessTokenExtractor {

	String extract(String accessTokenResponseBody) {
		if (accessTokenResponseBody == null) {
			return null;
		}

		String[] authenticationTokenParts = StringUtils.split(accessTokenResponseBody, "&");
		String[] accessTokenParts = StringUtils.split(authenticationTokenParts[0], "=");
		if (accessTokenParts.length != 2 || !StringUtils.equals(accessTokenParts[0], "access_token")) {
			return null;
		}

		return accessTokenParts[1];
	}

}

package com.cezarykluczynski.stapi.auth.oauth.session;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class OAuthSession {

	private Long accountId;

	private Long gitHubId;

	private String gitHubName;

	private List<String> permissions = Lists.newArrayList();

	OAuthSession copy() {
		OAuthSession copy = new OAuthSession();
		copy.setAccountId(accountId);
		copy.setGitHubId(gitHubId);
		copy.setGitHubName(gitHubName);
		copy.getPermissions().addAll(permissions);
		return copy;
	}

}

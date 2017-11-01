package com.cezarykluczynski.stapi.auth.oauth.session;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class OAuthSession {

	private Long gitHubId;

	private String gitHubName;

	private List<String> permissions = Lists.newArrayList();

	OAuthSession copy() {
		OAuthSession copy = new OAuthSession();
		copy.setGitHubId(gitHubId);
		copy.setGitHubName(gitHubName);
		copy.setPermissions(Lists.newArrayList(permissions));
		return copy;
	}

}

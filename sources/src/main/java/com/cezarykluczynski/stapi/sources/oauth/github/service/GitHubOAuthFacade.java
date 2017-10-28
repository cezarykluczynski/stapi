package com.cezarykluczynski.stapi.sources.oauth.github.service;

import com.cezarykluczynski.stapi.sources.oauth.github.dto.GitHubRedirectUrlDTO;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthFacade {

	public GitHubRedirectUrlDTO getGitHubRedirectUrl() {
		// TODO
		return new GitHubRedirectUrlDTO(null);
	}

	public GitHubRedirectUrlDTO authenticate(String code) {
		// TODO
		return new GitHubRedirectUrlDTO(null);
	}

}

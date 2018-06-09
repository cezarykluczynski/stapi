package com.cezarykluczynski.stapi.server.github.service;

import com.cezarykluczynski.stapi.server.github.model.GitHubDTO;
import com.google.common.cache.Cache;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GitHubApi {

	private static final String KEY = "KEY";

	private final GitHub gitHub;
	private final Cache<String, GitHubDTO> cache;

	public GitHubApi(GitHub gitHub, @Qualifier("gitHubCache") Cache<String, GitHubDTO> cache) {
		this.gitHub = gitHub;
		this.cache = cache;
	}

	public synchronized GitHubDTO getProjectDetails() {
		GitHubDTO gitHubDTO = cache.getIfPresent(KEY);
		if (gitHubDTO != null) {
			return gitHubDTO;
		}
		gitHubDTO = reload();
		cache.put(KEY, gitHubDTO);
		return gitHubDTO;
	}

	public GitHubDTO reload() {
		GitHubDTO gitHubDTO = new GitHubDTO();
		GHRepository ghRepository;
		try {
			ghRepository = gitHub.getRepository("cezarykluczynski/stapi");
		} catch (IOException e) {
			return gitHubDTO;
		}

		gitHubDTO.setStargazersCount(ghRepository.getStargazersCount());
		return gitHubDTO;
	}

}

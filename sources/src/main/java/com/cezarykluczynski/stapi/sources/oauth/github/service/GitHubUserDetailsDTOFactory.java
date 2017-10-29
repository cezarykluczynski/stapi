package com.cezarykluczynski.stapi.sources.oauth.github.service;

import com.cezarykluczynski.stapi.sources.oauth.github.dto.GitHubUserDetailsDTO;
import com.google.common.base.Preconditions;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GitHubUserDetailsDTOFactory {

	private static final String KEY_LOGIN = "login";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";

	GitHubUserDetailsDTO create(String userResponseBody) {
		JSONObject userResponseJson = new JSONObject(userResponseBody);
		GitHubUserDetailsDTO gitHubUserDetailsDTO = new GitHubUserDetailsDTO();
		gitHubUserDetailsDTO.setLogin(userResponseJson.has(KEY_LOGIN) ? userResponseJson.getString(KEY_LOGIN) : null);
		gitHubUserDetailsDTO.setId(userResponseJson.has(KEY_ID) ? userResponseJson.getLong(KEY_ID) : null);
		gitHubUserDetailsDTO.setName(userResponseJson.has(KEY_NAME) ? userResponseJson.getString(KEY_NAME) : null);
		gitHubUserDetailsDTO.setEmail(userResponseJson.has(KEY_EMAIL) ? userResponseJson.getString(KEY_EMAIL) : null);
		Preconditions.checkNotNull(gitHubUserDetailsDTO.getLogin(), "Login cannot be null");
		Preconditions.checkNotNull(gitHubUserDetailsDTO.getId(), "ID cannot be null");
		return gitHubUserDetailsDTO;
	}

}

package com.cezarykluczynski.stapi.auth.oauth.github.service;

import com.cezarykluczynski.stapi.auth.oauth.github.dto.GitHubUserDetailsDTO;
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
		gitHubUserDetailsDTO.setLogin(getString(userResponseJson, KEY_LOGIN));
		gitHubUserDetailsDTO.setId(getLong(userResponseJson, KEY_ID));
		gitHubUserDetailsDTO.setName(getString(userResponseJson, KEY_NAME));
		gitHubUserDetailsDTO.setEmail(getString(userResponseJson, KEY_EMAIL));
		Preconditions.checkNotNull(gitHubUserDetailsDTO.getLogin(), "Login cannot be null");
		Preconditions.checkNotNull(gitHubUserDetailsDTO.getId(), "ID cannot be null");
		return gitHubUserDetailsDTO;
	}

	private String getString(JSONObject userResponseJson, String key) {
		return userResponseJson.has(key) && !userResponseJson.isNull(key) ? userResponseJson.getString(key) : null;
	}

	private Long getLong(JSONObject userResponseJson, String key) {
		return userResponseJson.has(key) && !userResponseJson.isNull(key) ? userResponseJson.getLong(key) : null;
	}

}

package com.cezarykluczynski.stapi.auth.oauth.github.dto;

import lombok.Data;

@Data
public class GitHubUserDetailsDTO {

	private String login;

	private Long id;

	private String name;

	private String email;

}

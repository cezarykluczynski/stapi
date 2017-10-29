package com.cezarykluczynski.stapi.sources.oauth.github.dto;

import lombok.Data;

@Data
public class GitHubUserDetailsDTO {

	private String login;

	private Long id;

	private String name;

	private String email;

}

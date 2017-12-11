package com.cezarykluczynski.stapi.auth.account.dto;

import lombok.Data;

@Data
public class AccountDTO {

	private Long id;

	private Long gitHubAccountId;

	private String name;

	private String email;

}

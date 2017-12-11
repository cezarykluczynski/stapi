package com.cezarykluczynski.stapi.auth.account.operation.read;

import lombok.Data;

@Data
public class AccountsSearchCriteriaDTO {

	private int pageNumber;

	private Long id;

	private Long gitHubAccountId;

	private String name;

	private String email;

}

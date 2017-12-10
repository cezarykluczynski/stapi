package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import lombok.Data;

@Data
public class ApiKeysSearchCriteriaDTO {

	private int pageNumber;

	private Long accountId;

	private Long gitHubAccountId;

	private String name;

	private String email;

	private String apiKey;

}

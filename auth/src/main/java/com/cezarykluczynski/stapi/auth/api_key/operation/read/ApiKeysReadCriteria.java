package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeysReadCriteria {

	private Long accountId;

	private Long gitHubAccountId;

	private Long apiKeyId;

	private Integer pageNumber;

	private String name;

	private String email;

	private String apiKey;

}

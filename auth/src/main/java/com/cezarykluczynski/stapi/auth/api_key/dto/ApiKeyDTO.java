package com.cezarykluczynski.stapi.auth.api_key.dto;

import lombok.Data;

@Data
public class ApiKeyDTO {

	private Long id;

	private String apiKey;

	private Long accountId;

	private Integer limit;

	private Boolean blocked;

	private String description;

}

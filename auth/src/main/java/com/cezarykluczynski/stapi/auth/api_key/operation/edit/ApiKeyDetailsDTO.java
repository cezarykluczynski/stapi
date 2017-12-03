package com.cezarykluczynski.stapi.auth.api_key.operation.edit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyDetailsDTO {

	private Long apiKeyId;

	private String url;

	private String description;

}

package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyCreationResponseDTO {

	private boolean created;

	private FailReason failReason;

	private ApiKeyDTO apiKeyDTO;

	public enum FailReason {

		TOO_MUCH_KEYS_ALREADY_CREATED

	}

}

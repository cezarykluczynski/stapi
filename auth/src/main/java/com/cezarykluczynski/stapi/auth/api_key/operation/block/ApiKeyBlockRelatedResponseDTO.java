package com.cezarykluczynski.stapi.auth.api_key.operation.block;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiKeyBlockRelatedResponseDTO {

	private boolean successful;

	private FailReason failReason;

	public enum FailReason {

		KEY_DOES_NOT_EXIST,
		KEY_NOT_OWNED_BY_ACCOUNT

	}

}

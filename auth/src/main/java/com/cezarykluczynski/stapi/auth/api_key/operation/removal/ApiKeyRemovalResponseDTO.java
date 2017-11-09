package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyRemovalResponseDTO {

	private boolean removed;

	private FailReason failReason;

	public enum FailReason {

		BLOCKED_KEY,
		KEY_DOES_NOT_EXIST,
		KEY_NOT_OWNED_BY_ACCOUNT

	}

}

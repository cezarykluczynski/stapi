package com.cezarykluczynski.stapi.auth.api_key.operation.edit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyEditResponseDTO {

	private boolean successful;

	private boolean changed;

	private FailReason failReason;

	public enum FailReason {

		URL_TOO_LONG,
		DESCRIPTION_TOO_LONG

	}

}

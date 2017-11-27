package com.cezarykluczynski.stapi.auth.account.operation.edit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountEditResponseDTO {

	private boolean successful;

	private boolean changed;

	private FailReason failReason;

	public enum FailReason {

		INVALID_NAME,
		INVALID_EMAIL,
		CONSENTS_CANNOT_BE_SAVED

	}

}

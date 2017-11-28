package com.cezarykluczynski.stapi.auth.account.operation.read;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class AccountConsentsReadResponseDTO {

	private boolean successful;

	private Set<String> consentCodes;

	private FailReason failReason;

	public enum FailReason {

		CANNOT_RETRIEVE_CONSENTS

	}

}

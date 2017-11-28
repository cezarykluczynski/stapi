package com.cezarykluczynski.stapi.auth.account.operation.read;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountConsentsReadResponseDTOFactory {

	public AccountConsentsReadResponseDTO createSuccessful(Set<String> consentCodes) {
		return AccountConsentsReadResponseDTO.builder()
				.successful(true)
				.consentCodes(consentCodes)
				.build();
	}

	public AccountConsentsReadResponseDTO createUnsuccessful() {
		return AccountConsentsReadResponseDTO.builder()
				.successful(false)
				.failReason(AccountConsentsReadResponseDTO.FailReason.CANNOT_RETRIEVE_CONSENTS)
				.build();
	}

}

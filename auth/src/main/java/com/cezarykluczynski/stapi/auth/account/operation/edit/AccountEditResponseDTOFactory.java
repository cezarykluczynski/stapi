package com.cezarykluczynski.stapi.auth.account.operation.edit;

import org.springframework.stereotype.Service;

@Service
class AccountEditResponseDTOFactory {

	AccountEditResponseDTO createUnsuccessful(AccountEditResponseDTO.FailReason failReason) {
		return AccountEditResponseDTO.builder()
				.successful(false)
				.changed(false)
				.failReason(failReason)
				.build();
	}

	AccountEditResponseDTO createSuccessful() {
		return AccountEditResponseDTO.builder()
				.successful(true)
				.changed(true)
				.build();
	}

	AccountEditResponseDTO createUnchanged() {
		return AccountEditResponseDTO.builder()
				.successful(true)
				.changed(false)
				.build();
	}

}

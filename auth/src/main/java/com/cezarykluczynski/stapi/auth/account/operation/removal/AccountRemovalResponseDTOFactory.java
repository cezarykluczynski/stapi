package com.cezarykluczynski.stapi.auth.account.operation.removal;

import org.springframework.stereotype.Service;

@Service
class AccountRemovalResponseDTOFactory {

	AccountRemovalResponseDTO createSuccessful() {
		return AccountRemovalResponseDTO.builder()
				.successful(true)
				.build();
	}

	AccountRemovalResponseDTO createUnsuccessful() {
		return AccountRemovalResponseDTO.builder()
				.successful(false)
				.build();
	}

}

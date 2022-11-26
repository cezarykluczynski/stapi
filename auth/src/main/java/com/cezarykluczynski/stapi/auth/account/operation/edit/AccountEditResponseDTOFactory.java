package com.cezarykluczynski.stapi.auth.account.operation.edit;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
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

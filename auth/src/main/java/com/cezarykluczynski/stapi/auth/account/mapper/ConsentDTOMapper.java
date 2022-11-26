package com.cezarykluczynski.stapi.auth.account.mapper;

import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class ConsentDTOMapper {

	public ConsentDTO map(Consent consent) {
		return ConsentDTO.builder()
				.code(consent.getConsentType().name())
				.build();
	}

}

package com.cezarykluczynski.stapi.auth.account.mapper;

import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import org.springframework.stereotype.Service;

@Service
public class ConsentDTOMapper {

	public ConsentDTO map(Consent consent) {
		return ConsentDTO.builder()
				.code(consent.getConsentType().name())
				.build();
	}

}

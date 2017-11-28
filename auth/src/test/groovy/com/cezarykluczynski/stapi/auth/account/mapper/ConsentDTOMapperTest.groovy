package com.cezarykluczynski.stapi.auth.account.mapper

import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO
import com.cezarykluczynski.stapi.model.consent.entity.Consent
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import spock.lang.Specification

class ConsentDTOMapperTest extends Specification {

	private ConsentDTOMapper consentDTOMapper

	void setup() {
		consentDTOMapper = new ConsentDTOMapper()
	}

	void "maps Consent to ConsentDTO"() {
		given:
		Consent consent = new Consent(consentType: ConsentType.TECHNICAL_MAILING)

		when:
		ConsentDTO consentDTO = consentDTOMapper.map(consent)

		then:
		consentDTO.code == ConsentType.TECHNICAL_MAILING.name()
	}

}

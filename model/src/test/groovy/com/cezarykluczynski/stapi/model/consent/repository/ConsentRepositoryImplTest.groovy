package com.cezarykluczynski.stapi.model.consent.repository

import com.cezarykluczynski.stapi.model.consent.entity.Consent
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType
import com.google.common.collect.Lists
import spock.lang.Specification

class ConsentRepositoryImplTest extends Specification {

	private ConsentRepository consentRepositoryMock

	private ConsentRepositoryImpl consentRepositoryImpl

	void setup() {
		consentRepositoryMock = Mock()
		consentRepositoryImpl = new ConsentRepositoryImpl()
		consentRepositoryImpl.consentRepository = consentRepositoryMock
	}

	void "ensures ale consents exists in the database"() {
		when:
		consentRepositoryImpl.ensureExists()

		then:
		1 * consentRepositoryMock.findAll() >> Lists.newArrayList()
		1 * consentRepositoryMock.save(_ as Iterable) >> { List<Consent> consents ->
			consents.size() == 1
			consents[0].consentType == ConsentType.TECHNICAL_MAILING
			consents
		}
		0 * _
	}

}

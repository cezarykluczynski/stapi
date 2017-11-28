package com.cezarykluczynski.stapi.auth.account.api

import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO
import com.cezarykluczynski.stapi.auth.account.mapper.ConsentDTOMapper
import com.cezarykluczynski.stapi.model.consent.entity.Consent
import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class ConsentApiTest extends Specification {

	private ConsentRepository consentRepositoryMock

	private ConsentDTOMapper consentDTOMapperMock

	private ConsentApi consentApi

	void setup() {
		consentRepositoryMock = Mock()
		consentDTOMapperMock = Mock()
		consentApi = new ConsentApi(consentRepositoryMock, consentDTOMapperMock)
	}

	void "provides all consents"() {
		given:
		Consent consent1 = Mock()
		Consent consent2 = Mock()
		ConsentDTO consentDTO1 = Mock()
		ConsentDTO consentDTO2 = Mock()

		when:
		List<ConsentDTO> consentDTOList = consentApi.provideAll()

		then:
		1 * consentRepositoryMock.findAll() >> Lists.newArrayList(consent1, consent2)
		1 * consentDTOMapperMock.map(consent1) >> consentDTO1
		1 * consentDTOMapperMock.map(consent2) >> consentDTO2
		0 * _
		consentDTOList.size() == 2
		consentDTOList[0] == consentDTO1
		consentDTOList[1] == consentDTO2
	}

}

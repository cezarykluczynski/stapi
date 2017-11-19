package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.LiteratureApi
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.util.AbstractLiteratureTest

class LiteratureTest extends AbstractLiteratureTest {

	private LiteratureApi literatureApiMock

	private Literature literature

	void setup() {
		literatureApiMock = Mock()
		literature = new Literature(literatureApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		LiteratureFullResponse literatureFullResponse = Mock()

		when:
		LiteratureFullResponse literatureFullResponseOutput = literature.get(UID)

		then:
		1 * literatureApiMock.literatureGet(UID, API_KEY) >> literatureFullResponse
		0 * _
		literatureFullResponse == literatureFullResponseOutput
	}

	void "searches entities"() {
		given:
		LiteratureBaseResponse literatureBaseResponse = Mock()

		when:
		LiteratureBaseResponse literatureBaseResponseOutput = literature.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, EARTHLY_ORIGIN,
				SHAKESPEAREAN_WORK, REPORT, SCIENTIFIC_LITERATURE, TECHNICAL_MANUAL, RELIGIOUS_LITERATURE)

		then:
		1 * literatureApiMock.literatureSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, EARTHLY_ORIGIN, SHAKESPEAREAN_WORK, REPORT,
				SCIENTIFIC_LITERATURE, TECHNICAL_MANUAL, RELIGIOUS_LITERATURE) >> literatureBaseResponse
		0 * _
		literatureBaseResponse == literatureBaseResponseOutput
	}

}

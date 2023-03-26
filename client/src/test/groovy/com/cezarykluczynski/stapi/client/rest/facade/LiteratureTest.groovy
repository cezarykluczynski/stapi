package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.LiteratureApi
import com.cezarykluczynski.stapi.client.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.client.rest.model.LiteratureSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractLiteratureTest

class LiteratureTest extends AbstractLiteratureTest {

	private LiteratureApi literatureApiMock

	private Literature literature

	void setup() {
		literatureApiMock = Mock()
		literature = new Literature(literatureApiMock)
	}

	void "gets single entity"() {
		given:
		LiteratureFullResponse literatureFullResponse = Mock()

		when:
		LiteratureFullResponse literatureFullResponseOutput = literature.get(UID)

		then:
		1 * literatureApiMock.v1GetLiterature(UID) >> literatureFullResponse
		0 * _
		literatureFullResponse == literatureFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		LiteratureBaseResponse literatureBaseResponse = Mock()
		LiteratureSearchCriteria literatureSearchCriteria = new LiteratureSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				earthlyOrigin: EARTHLY_ORIGIN,
				shakespeareanWork: SHAKESPEAREAN_WORK,
				report: REPORT,
				scientificLiterature: SCIENTIFIC_LITERATURE,
				technicalManual: TECHNICAL_MANUAL,
				religiousLiterature: RELIGIOUS_LITERATURE)
		literatureSearchCriteria.sort = SORT

		when:
		LiteratureBaseResponse literatureBaseResponseOutput = literature.search(literatureSearchCriteria)

		then:
		1 * literatureApiMock.v1SearchLiterature(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, EARTHLY_ORIGIN, SHAKESPEAREAN_WORK,
				REPORT, SCIENTIFIC_LITERATURE, TECHNICAL_MANUAL, RELIGIOUS_LITERATURE) >> literatureBaseResponse
		0 * _
		literatureBaseResponse == literatureBaseResponseOutput
	}

}

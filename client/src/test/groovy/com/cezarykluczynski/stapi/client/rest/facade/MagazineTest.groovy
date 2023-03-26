package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.MagazineApi
import com.cezarykluczynski.stapi.client.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MagazineFullResponse
import com.cezarykluczynski.stapi.client.rest.model.MagazineSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractMagazineTest

class MagazineTest extends AbstractMagazineTest {

	private MagazineApi magazineApiMock

	private Magazine magazine

	void setup() {
		magazineApiMock = Mock()
		magazine = new Magazine(magazineApiMock)
	}

	void "gets single entity"() {
		given:
		MagazineFullResponse magazineFullResponse = Mock()

		when:
		MagazineFullResponse magazineFullResponseOutput = magazine.get(UID)

		then:
		1 * magazineApiMock.v1GetMagazine(UID) >> magazineFullResponse
		0 * _
		magazineFullResponse == magazineFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		MagazineBaseResponse magazineBaseResponse = Mock()
		MagazineSearchCriteria magazineSearchCriteria = new MagazineSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO)
		magazineSearchCriteria.sort = SORT

		when:
		MagazineBaseResponse magazineBaseResponseOutput = magazine.search(magazineSearchCriteria)

		then:
		1 * magazineApiMock.v1SearchMagazines(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO) >> magazineBaseResponse
		0 * _
		magazineBaseResponse == magazineBaseResponseOutput
	}

}

package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineApi
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse
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
		1 * magazineApiMock.v1RestMagazineGet(UID, null) >> magazineFullResponse
		0 * _
		magazineFullResponse == magazineFullResponseOutput
	}

	void "searches entities"() {
		given:
		MagazineBaseResponse magazineBaseResponse = Mock()

		when:
		MagazineBaseResponse magazineBaseResponseOutput = magazine.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		then:
		1 * magazineApiMock.v1RestMagazineSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO) >> magazineBaseResponse
		0 * _
		magazineBaseResponse == magazineBaseResponseOutput
	}

}

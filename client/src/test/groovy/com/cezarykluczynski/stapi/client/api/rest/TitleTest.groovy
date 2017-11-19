package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TitleApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse
import com.cezarykluczynski.stapi.util.AbstractTitleTest

class TitleTest extends AbstractTitleTest {

	private TitleApi titleApiMock

	private Title title

	void setup() {
		titleApiMock = Mock()
		title = new Title(titleApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		TitleFullResponse titleFullResponse = Mock()

		when:
		TitleFullResponse titleFullResponseOutput = title.get(UID)

		then:
		1 * titleApiMock.titleGet(UID, API_KEY) >> titleFullResponse
		0 * _
		titleFullResponse == titleFullResponseOutput
	}

	void "searches entities"() {
		given:
		TitleBaseResponse titleBaseResponse = Mock()

		when:
		TitleBaseResponse titleBaseResponseOutput = title.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE,
				POSITION, MIRROR)

		then:
		1 * titleApiMock.titleSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE, POSITION,
				MIRROR) >> titleBaseResponse
		0 * _
		titleBaseResponse == titleBaseResponseOutput
	}

}

package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TitleApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractTitleTest

class TitleTest extends AbstractTitleTest {

	private TitleApi titleApiMock

	private Title title

	void setup() {
		titleApiMock = Mock()
		title = new Title(titleApiMock)
	}

	void "gets single entity"() {
		given:
		TitleFullResponse titleFullResponse = Mock()

		when:
		TitleFullResponse titleFullResponseOutput = title.get(UID)

		then:
		1 * titleApiMock.v1RestTitleGet(UID, null) >> titleFullResponse
		0 * _
		titleFullResponse == titleFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		TitleV2FullResponse titleV2FullResponse = Mock()

		when:
		TitleV2FullResponse titleFullResponseOutput = title.getV2(UID)

		then:
		1 * titleApiMock.v2RestTitleGet(UID) >> titleV2FullResponse
		0 * _
		titleV2FullResponse == titleFullResponseOutput
	}

	void "searches entities"() {
		given:
		TitleBaseResponse titleBaseResponse = Mock()

		when:
		TitleBaseResponse titleBaseResponseOutput = title.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE,
				POSITION, MIRROR)

		then:
		1 * titleApiMock.v1RestTitleSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE, POSITION,
				MIRROR) >> titleBaseResponse
		0 * _
		titleBaseResponse == titleBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		TitleV2BaseResponse titleV2BaseResponse = Mock()

		when:
		TitleV2BaseResponse titleBaseResponseOutput = title.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE,
				EDUCATION_TITLE, MIRROR)

		then:
		1 * titleApiMock.v2RestTitleSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE,
				EDUCATION_TITLE, MIRROR) >> titleV2BaseResponse
		0 * _
		titleV2BaseResponse == titleBaseResponseOutput
	}

}

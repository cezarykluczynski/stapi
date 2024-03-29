package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.TitleApi
import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.TitleV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractTitleTest

class TitleTest extends AbstractTitleTest {

	private TitleApi titleApiMock

	private Title title

	void setup() {
		titleApiMock = Mock()
		title = new Title(titleApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		TitleV2FullResponse titleV2FullResponse = Mock()

		when:
		TitleV2FullResponse titleFullResponseOutput = title.getV2(UID)

		then:
		1 * titleApiMock.v2GetTitle(UID) >> titleV2FullResponse
		0 * _
		titleV2FullResponse == titleFullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		TitleV2BaseResponse titleV2BaseResponse = Mock()
		TitleV2SearchCriteria titleV2SearchCriteria = new TitleV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				militaryRank: MILITARY_RANK,
				fleetRank: FLEET_RANK,
				religiousTitle: RELIGIOUS_TITLE,
				educationTitle: EDUCATION_TITLE,
				mirror: MIRROR)
		titleV2SearchCriteria.sort = SORT

		when:
		TitleV2BaseResponse titleBaseResponseOutput = title.searchV2(titleV2SearchCriteria)

		then:
		1 * titleApiMock.v2SearchTitles(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, MILITARY_RANK, FLEET_RANK, RELIGIOUS_TITLE,
				EDUCATION_TITLE, MIRROR) >> titleV2BaseResponse
		0 * _
		titleV2BaseResponse == titleBaseResponseOutput
	}

}

package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.LocationApi
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractLocationTest

class LocationTest extends AbstractLocationTest {

	private LocationApi locationApiMock

	private Location location

	void setup() {
		locationApiMock = Mock()
		location = new Location(locationApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		LocationFullResponse locationFullResponse = Mock()

		when:
		LocationFullResponse locationFullResponseOutput = location.get(UID)

		then:
		1 * locationApiMock.v1RestLocationGet(UID, API_KEY) >> locationFullResponse
		0 * _
		locationFullResponse == locationFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		LocationV2FullResponse locationV2FullResponse = Mock()

		when:
		LocationV2FullResponse locationV2FullResponseOutput = location.getV2(UID)

		then:
		1 * locationApiMock.v2RestLocationGet(UID, API_KEY) >> locationV2FullResponse
		0 * _
		locationV2FullResponse == locationV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		LocationBaseResponse locationBaseResponse = Mock()

		when:
		LocationBaseResponse locationBaseResponseOutput = location.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, EARTHLY_LOCATION, FICTIONAL_LOCATION,
				RELIGIOUS_LOCATION, GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY, SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT,
				COLONY, LANDFORM, LANDMARK, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT, MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT,
				SCHOOL, MIRROR, ALTERNATE_REALITY)

		then:
		1 * locationApiMock.v1RestLocationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, EARTHLY_LOCATION, FICTIONAL_LOCATION, RELIGIOUS_LOCATION,
				GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY, SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT, COLONY, LANDFORM,
				LANDMARK, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT, MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT, SCHOOL, MIRROR,
				ALTERNATE_REALITY) >> locationBaseResponse
		0 * _
		locationBaseResponse == locationBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		LocationV2BaseResponse locationV2BaseResponse = Mock()

		when:
		LocationV2BaseResponse locationV2BaseResponseOutput = location.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, EARTHLY_LOCATION, QONOS_LOCATION,
				FICTIONAL_LOCATION, MYTHOLOGICAL_LOCATION, RELIGIOUS_LOCATION, GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY,
				SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT, COLONY, LANDFORM, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT,
				MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT, SCHOOL, RESTAURANT, RESIDENCE, MIRROR, ALTERNATE_REALITY)

		then:
		1 * locationApiMock.v2RestLocationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, EARTHLY_LOCATION, QONOS_LOCATION,
				FICTIONAL_LOCATION, MYTHOLOGICAL_LOCATION, RELIGIOUS_LOCATION, GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY,
				SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT, COLONY, LANDFORM, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT,
				MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT, SCHOOL, RESTAURANT, RESIDENCE, MIRROR, ALTERNATE_REALITY) >> locationV2BaseResponse
		0 * _
		locationV2BaseResponse == locationV2BaseResponseOutput
	}

}

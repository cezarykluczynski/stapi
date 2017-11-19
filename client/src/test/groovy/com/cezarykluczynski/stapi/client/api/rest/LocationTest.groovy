package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.LocationApi
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse
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
		1 * locationApiMock.locationGet(UID, API_KEY) >> locationFullResponse
		0 * _
		locationFullResponse == locationFullResponseOutput
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
		1 * locationApiMock.locationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, EARTHLY_LOCATION, FICTIONAL_LOCATION, RELIGIOUS_LOCATION,
				GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY, SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT, COLONY, LANDFORM,
				LANDMARK, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT, MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT, SCHOOL, MIRROR,
				ALTERNATE_REALITY) >> locationBaseResponse
		0 * _
		locationBaseResponse == locationBaseResponseOutput
	}

}

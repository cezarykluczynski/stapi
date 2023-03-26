package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.LocationApi
import com.cezarykluczynski.stapi.client.rest.model.LocationV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.LocationV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.LocationV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractLocationTest

class LocationTest extends AbstractLocationTest {

	private LocationApi locationApiMock

	private Location location

	void setup() {
		locationApiMock = Mock()
		location = new Location(locationApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		LocationV2FullResponse locationV2FullResponse = Mock()

		when:
		LocationV2FullResponse locationV2FullResponseOutput = location.getV2(UID)

		then:
		1 * locationApiMock.v2GetLocation(UID) >> locationV2FullResponse
		0 * _
		locationV2FullResponse == locationV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		LocationV2BaseResponse locationV2BaseResponse = Mock()
		LocationV2SearchCriteria locationV2SearchCriteria = new LocationV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				earthlyLocation: EARTHLY_LOCATION,
				qonosLocation: QONOS_LOCATION,
				fictionalLocation: FICTIONAL_LOCATION,
				religiousLocation: RELIGIOUS_LOCATION,
				mythologicalLocation: MYTHOLOGICAL_LOCATION,
				geographicalLocation: GEOGRAPHICAL_LOCATION,
				bodyOfWater: BODY_OF_WATER,
				country: COUNTRY,
				subnationalEntity: SUBNATIONAL_ENTITY,
				settlement: SETTLEMENT,
				usSettlement: US_SETTLEMENT,
				bajoranSettlement: BAJORAN_SETTLEMENT,
				colony: COLONY,
				landform: LANDFORM,
				road: ROAD,
				structure: STRUCTURE,
				shipyard: SHIPYARD,
				buildingInterior: BUILDING_INTERIOR,
				establishment: ESTABLISHMENT,
				medicalEstablishment: MEDICAL_ESTABLISHMENT,
				ds9Establishment: DS9_ESTABLISHMENT,
				school: SCHOOL,
				restaurant: RESTAURANT,
				residence: RESIDENCE,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		locationV2SearchCriteria.sort = SORT

		when:
		LocationV2BaseResponse locationV2BaseResponseOutput = location.searchV2(locationV2SearchCriteria)

		then:
		1 * locationApiMock.v2SearchLocations(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EARTHLY_LOCATION, QONOS_LOCATION,
				FICTIONAL_LOCATION, MYTHOLOGICAL_LOCATION, RELIGIOUS_LOCATION, GEOGRAPHICAL_LOCATION, BODY_OF_WATER, COUNTRY, SUBNATIONAL_ENTITY,
				SETTLEMENT, US_SETTLEMENT, BAJORAN_SETTLEMENT, COLONY, LANDFORM, ROAD, STRUCTURE, SHIPYARD, BUILDING_INTERIOR, ESTABLISHMENT,
				MEDICAL_ESTABLISHMENT, DS9_ESTABLISHMENT, SCHOOL, RESTAURANT, RESIDENCE, MIRROR, ALTERNATE_REALITY) >> locationV2BaseResponse
		0 * _
		locationV2BaseResponse == locationV2BaseResponseOutput
	}

}

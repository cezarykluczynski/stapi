package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.util.AbstractLocationTest

abstract class AbstractLocationMapperTest extends AbstractLocationTest {

	protected static Location createLocation() {
		new Location(
				uid: UID,
				name: NAME,
				earthlyLocation: EARTHLY_LOCATION,
				fictionalLocation: FICTIONAL_LOCATION,
				religiousLocation: RELIGIOUS_LOCATION,
				geographicalLocation: GEOGRAPHICAL_LOCATION,
				bodyOfWater: BODY_OF_WATER,
				country: COUNTRY,
				subnationalEntity: SUBNATIONAL_ENTITY,
				settlement: SETTLEMENT,
				usSettlement: US_SETTLEMENT,
				bajoranSettlement: BAJORAN_SETTLEMENT,
				colony: COLONY,
				landform: LANDFORM,
				landmark: LANDMARK,
				road: ROAD,
				structure: STRUCTURE,
				shipyard: SHIPYARD,
				buildingInterior: BUILDING_INTERIOR,
				establishment: ESTABLISHMENT,
				medicalEstablishment: MEDICAL_ESTABLISHMENT,
				ds9Establishment: DS9_ESTABLISHMENT,
				school: SCHOOL,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
	}

}

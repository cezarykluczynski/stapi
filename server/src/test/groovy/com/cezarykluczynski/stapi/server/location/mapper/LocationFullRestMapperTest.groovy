package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFull
import com.cezarykluczynski.stapi.model.location.entity.Location
import org.mapstruct.factory.Mappers

class LocationFullRestMapperTest extends AbstractLocationMapperTest {

	private LocationFullRestMapper locationFullRestMapper

	void setup() {
		locationFullRestMapper = Mappers.getMapper(LocationFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Location dBLocation = createLocation()

		when:
		LocationFull locationFull = locationFullRestMapper.mapFull(dBLocation)

		then:
		locationFull.uid == UID
		locationFull.name == NAME
		locationFull.earthlyLocation == EARTHLY_LOCATION
		locationFull.fictionalLocation == FICTIONAL_LOCATION
		locationFull.religiousLocation == RELIGIOUS_LOCATION
		locationFull.geographicalLocation == GEOGRAPHICAL_LOCATION
		locationFull.bodyOfWater == BODY_OF_WATER
		locationFull.country == COUNTRY
		locationFull.subnationalEntity == SUBNATIONAL_ENTITY
		locationFull.settlement == SETTLEMENT
		locationFull.usSettlement == US_SETTLEMENT
		locationFull.bajoranSettlement == BAJORAN_SETTLEMENT
		locationFull.colony == COLONY
		locationFull.landform == LANDFORM
		locationFull.landmark == LANDMARK
		locationFull.road == ROAD
		locationFull.structure == STRUCTURE
		locationFull.shipyard == SHIPYARD
		locationFull.buildingInterior == BUILDING_INTERIOR
		locationFull.establishment == ESTABLISHMENT
		locationFull.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationFull.ds9Establishment == DS9_ESTABLISHMENT
		locationFull.mirror == MIRROR
		locationFull.alternateReality == ALTERNATE_REALITY
	}

}

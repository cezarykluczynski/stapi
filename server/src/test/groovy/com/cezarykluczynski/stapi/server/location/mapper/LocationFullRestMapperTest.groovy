package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFull
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2Full
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
		!locationFull.landmark
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Location dBLocation = createLocation()

		when:
		LocationV2Full locationV2Full = locationFullRestMapper.mapV2Full(dBLocation)

		then:
		locationV2Full.uid == UID
		locationV2Full.name == NAME
		locationV2Full.earthlyLocation == EARTHLY_LOCATION
		locationV2Full.qonosLocation == QONOS_LOCATION
		locationV2Full.fictionalLocation == FICTIONAL_LOCATION
		locationV2Full.mythologicalLocation == MYTHOLOGICAL_LOCATION
		locationV2Full.religiousLocation == RELIGIOUS_LOCATION
		locationV2Full.geographicalLocation == GEOGRAPHICAL_LOCATION
		locationV2Full.bodyOfWater == BODY_OF_WATER
		locationV2Full.country == COUNTRY
		locationV2Full.subnationalEntity == SUBNATIONAL_ENTITY
		locationV2Full.settlement == SETTLEMENT
		locationV2Full.usSettlement == US_SETTLEMENT
		locationV2Full.bajoranSettlement == BAJORAN_SETTLEMENT
		locationV2Full.colony == COLONY
		locationV2Full.landform == LANDFORM
		locationV2Full.road == ROAD
		locationV2Full.structure == STRUCTURE
		locationV2Full.shipyard == SHIPYARD
		locationV2Full.buildingInterior == BUILDING_INTERIOR
		locationV2Full.establishment == ESTABLISHMENT
		locationV2Full.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationV2Full.ds9Establishment == DS9_ESTABLISHMENT
		locationV2Full.school == SCHOOL
		locationV2Full.restaurant == RESTAURANT
		locationV2Full.residence == RESIDENCE
		locationV2Full.mirror == MIRROR
		locationV2Full.alternateReality == ALTERNATE_REALITY
	}

}

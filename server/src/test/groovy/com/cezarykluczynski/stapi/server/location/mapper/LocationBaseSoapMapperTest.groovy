package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LocationBase
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LocationBaseSoapMapperTest extends AbstractLocationMapperTest {

	private LocationBaseSoapMapper locationBaseSoapMapper

	void setup() {
		locationBaseSoapMapper = Mappers.getMapper(LocationBaseSoapMapper)
	}

	void "maps SOAP LocationBaseRequest to LocationRequestDTO"() {
		given:
		LocationBaseRequest locationBaseRequest = new LocationBaseRequest(
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

		when:
		LocationRequestDTO locationRequestDTO = locationBaseSoapMapper.mapBase locationBaseRequest

		then:
		locationRequestDTO.name == NAME
		locationRequestDTO.earthlyLocation == EARTHLY_LOCATION
		locationRequestDTO.fictionalLocation == FICTIONAL_LOCATION
		locationRequestDTO.religiousLocation == RELIGIOUS_LOCATION
		locationRequestDTO.geographicalLocation == GEOGRAPHICAL_LOCATION
		locationRequestDTO.bodyOfWater == BODY_OF_WATER
		locationRequestDTO.country == COUNTRY
		locationRequestDTO.subnationalEntity == SUBNATIONAL_ENTITY
		locationRequestDTO.settlement == SETTLEMENT
		locationRequestDTO.usSettlement == US_SETTLEMENT
		locationRequestDTO.bajoranSettlement == BAJORAN_SETTLEMENT
		locationRequestDTO.colony == COLONY
		locationRequestDTO.landform == LANDFORM
		locationRequestDTO.landmark == LANDMARK
		locationRequestDTO.road == ROAD
		locationRequestDTO.structure == STRUCTURE
		locationRequestDTO.shipyard == SHIPYARD
		locationRequestDTO.buildingInterior == BUILDING_INTERIOR
		locationRequestDTO.establishment == ESTABLISHMENT
		locationRequestDTO.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationRequestDTO.ds9Establishment == DS9_ESTABLISHMENT
		locationRequestDTO.mirror == MIRROR
		locationRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Location location = createLocation()

		when:
		LocationBase locationBase = locationBaseSoapMapper.mapBase(Lists.newArrayList(location))[0]

		then:
		locationBase.uid == UID
		locationBase.name == NAME
		locationBase.earthlyLocation == EARTHLY_LOCATION
		locationBase.fictionalLocation == FICTIONAL_LOCATION
		locationBase.religiousLocation == RELIGIOUS_LOCATION
		locationBase.geographicalLocation == GEOGRAPHICAL_LOCATION
		locationBase.bodyOfWater == BODY_OF_WATER
		locationBase.country == COUNTRY
		locationBase.subnationalEntity == SUBNATIONAL_ENTITY
		locationBase.settlement == SETTLEMENT
		locationBase.usSettlement == US_SETTLEMENT
		locationBase.bajoranSettlement == BAJORAN_SETTLEMENT
		locationBase.colony == COLONY
		locationBase.landform == LANDFORM
		locationBase.landmark == LANDMARK
		locationBase.road == ROAD
		locationBase.structure == STRUCTURE
		locationBase.shipyard == SHIPYARD
		locationBase.buildingInterior == BUILDING_INTERIOR
		locationBase.establishment == ESTABLISHMENT
		locationBase.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationBase.ds9Establishment == DS9_ESTABLISHMENT
		locationBase.mirror == MIRROR
		locationBase.alternateReality == ALTERNATE_REALITY
	}

}

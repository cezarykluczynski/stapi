package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.rest.model.LocationBase
import com.cezarykluczynski.stapi.client.rest.model.LocationV2Base
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LocationBaseRestMapperTest extends AbstractLocationMapperTest {

	private LocationBaseRestMapper locationBaseRestMapper

	void setup() {
		locationBaseRestMapper = Mappers.getMapper(LocationBaseRestMapper)
	}

	void "maps LocationRestBeanParams to LocationRequestDTO"() {
		given:
		LocationRestBeanParams locationRestBeanParams = new LocationRestBeanParams(
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
		LocationRequestDTO locationRequestDTO = locationBaseRestMapper.mapBase locationRestBeanParams

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
		locationRequestDTO.school == SCHOOL
		locationRequestDTO.mirror == MIRROR
		locationRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps LocationV2RestBeanParams to LocationRequestDTO"() {
		given:
		LocationV2RestBeanParams locationV2RestBeanParams = new LocationV2RestBeanParams(
				name: NAME,
				earthlyLocation: EARTHLY_LOCATION,
				qonosLocation: QONOS_LOCATION,
				fictionalLocation: FICTIONAL_LOCATION,
				mythologicalLocation: MYTHOLOGICAL_LOCATION,
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

		when:
		LocationRequestDTO locationRequestDTO = locationBaseRestMapper.mapV2Base locationV2RestBeanParams

		then:
		locationRequestDTO.name == NAME
		locationRequestDTO.earthlyLocation == EARTHLY_LOCATION
		locationRequestDTO.qonosLocation == QONOS_LOCATION
		locationRequestDTO.fictionalLocation == FICTIONAL_LOCATION
		locationRequestDTO.mythologicalLocation == MYTHOLOGICAL_LOCATION
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
		locationRequestDTO.road == ROAD
		locationRequestDTO.structure == STRUCTURE
		locationRequestDTO.shipyard == SHIPYARD
		locationRequestDTO.buildingInterior == BUILDING_INTERIOR
		locationRequestDTO.establishment == ESTABLISHMENT
		locationRequestDTO.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationRequestDTO.ds9Establishment == DS9_ESTABLISHMENT
		locationRequestDTO.school == SCHOOL
		locationRequestDTO.restaurant == RESTAURANT
		locationRequestDTO.residence == RESIDENCE
		locationRequestDTO.mirror == MIRROR
		locationRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		Location location = createLocation()

		when:
		LocationBase locationBase = locationBaseRestMapper.mapBase(Lists.newArrayList(location))[0]

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
		!locationBase.landmark
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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Location location = createLocation()

		when:
		LocationV2Base locationV2Base = locationBaseRestMapper.mapV2Base(Lists.newArrayList(location))[0]

		then:
		locationV2Base.uid == UID
		locationV2Base.name == NAME
		locationV2Base.earthlyLocation == EARTHLY_LOCATION
		locationV2Base.qonosLocation == QONOS_LOCATION
		locationV2Base.fictionalLocation == FICTIONAL_LOCATION
		locationV2Base.mythologicalLocation == MYTHOLOGICAL_LOCATION
		locationV2Base.religiousLocation == RELIGIOUS_LOCATION
		locationV2Base.geographicalLocation == GEOGRAPHICAL_LOCATION
		locationV2Base.bodyOfWater == BODY_OF_WATER
		locationV2Base.country == COUNTRY
		locationV2Base.subnationalEntity == SUBNATIONAL_ENTITY
		locationV2Base.settlement == SETTLEMENT
		locationV2Base.usSettlement == US_SETTLEMENT
		locationV2Base.bajoranSettlement == BAJORAN_SETTLEMENT
		locationV2Base.colony == COLONY
		locationV2Base.landform == LANDFORM
		locationV2Base.road == ROAD
		locationV2Base.structure == STRUCTURE
		locationV2Base.shipyard == SHIPYARD
		locationV2Base.buildingInterior == BUILDING_INTERIOR
		locationV2Base.establishment == ESTABLISHMENT
		locationV2Base.medicalEstablishment == MEDICAL_ESTABLISHMENT
		locationV2Base.ds9Establishment == DS9_ESTABLISHMENT
		locationV2Base.school == SCHOOL
		locationV2Base.restaurant == RESTAURANT
		locationV2Base.residence == RESIDENCE
		locationV2Base.mirror == MIRROR
		locationV2Base.alternateReality == ALTERNATE_REALITY
	}

}

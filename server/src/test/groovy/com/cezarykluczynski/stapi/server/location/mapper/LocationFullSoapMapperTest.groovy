package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LocationFull
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.entity.Location
import org.mapstruct.factory.Mappers

class LocationFullSoapMapperTest extends AbstractLocationMapperTest {

	private LocationFullSoapMapper locationFullSoapMapper

	void setup() {
		locationFullSoapMapper = Mappers.getMapper(LocationFullSoapMapper)
	}

	void "maps SOAP LocationFullRequest to LocationBaseRequestDTO"() {
		given:
		LocationFullRequest locationFullRequest = new LocationFullRequest(uid: UID)

		when:
		LocationRequestDTO locationRequestDTO = locationFullSoapMapper.mapFull locationFullRequest

		then:
		locationRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Location location = createLocation()

		when:
		LocationFull locationFull = locationFullSoapMapper.mapFull(location)

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

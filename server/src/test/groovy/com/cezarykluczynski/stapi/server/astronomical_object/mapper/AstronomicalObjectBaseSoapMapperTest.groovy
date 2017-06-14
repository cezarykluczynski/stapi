package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase as AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectBaseSoapMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper

	void setup() {
		astronomicalObjectBaseSoapMapper = Mappers.getMapper(AstronomicalObjectBaseSoapMapper)
	}

	void "maps SOAP AstronomicalObjectBaseRequest to AstronomicalObjectRequestDTO"() {
		given:
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = new AstronomicalObjectBaseRequest(
				name: NAME,
				astronomicalObjectType: SOAP_ASTRONOMICAL_OBJECT_TYPE,
				locationUid: LOCATION_UID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectBaseSoapMapper.mapBase astronomicalObjectBaseRequest

		then:
		astronomicalObjectRequestDTO.name == NAME
		astronomicalObjectRequestDTO.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectRequestDTO.locationUid == LOCATION_UID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		AstronomicalObject astronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectBase astronomicalObjectBase = astronomicalObjectBaseSoapMapper.mapBase(Lists.newArrayList(astronomicalObject))[0]

		then:
		astronomicalObjectBase.uid == UID
		astronomicalObjectBase.name == NAME
		astronomicalObjectBase.astronomicalObjectType == SOAP_ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectBase.location != null
	}

}

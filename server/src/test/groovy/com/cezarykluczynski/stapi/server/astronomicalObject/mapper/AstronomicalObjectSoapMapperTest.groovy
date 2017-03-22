package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase as AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectSoapMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapper

	void setup() {
		astronomicalObjectSoapMapper = Mappers.getMapper(AstronomicalObjectSoapMapper)
	}

	void "maps SOAP AstronomicalObjectBaseRequest to AstronomicalObjectRequestDTO"() {
		given:
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = new AstronomicalObjectBaseRequest(
				name: NAME,
				astronomicalObjectType: SOAP_ASTRONOMICAL_OBJECT_TYPE,
				locationGuid: LOCATION_GUID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectSoapMapper.mapBase astronomicalObjectBaseRequest

		then:
		astronomicalObjectRequestDTO.name == NAME
		astronomicalObjectRequestDTO.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectRequestDTO.locationGuid == LOCATION_GUID
	}

	void "maps SOAP AstronomicalObjectFullRequest to AstronomicalObjectBaseRequestDTO"() {
		given:
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = new AstronomicalObjectFullRequest(guid: GUID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectSoapMapper.mapFull astronomicalObjectFullRequest

		then:
		astronomicalObjectRequestDTO.guid == GUID
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		AstronomicalObject astronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectBase astronomicalObjectBase = astronomicalObjectSoapMapper.mapBase(Lists.newArrayList(astronomicalObject))[0]

		then:
		astronomicalObjectBase.guid == GUID
		astronomicalObjectBase.name == NAME
		astronomicalObjectBase.astronomicalObjectType == SOAP_ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectBase.locationHeader != null
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		AstronomicalObject dBAstronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectFull astronomicalObjectFull = astronomicalObjectSoapMapper.mapFull(dBAstronomicalObject)

		then:
		astronomicalObjectFull.guid == GUID
		astronomicalObjectFull.name == NAME
		astronomicalObjectFull.astronomicalObjectType == SOAP_ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectFull.location != null
	}

}

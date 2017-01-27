package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObject as SOAPAstronomicalObject
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject as DBAstronomicalObject
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectSoapMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapper

	void setup() {
		astronomicalObjectSoapMapper = Mappers.getMapper(AstronomicalObjectSoapMapper)
	}

	void "maps SOAP AstronomicalObjectRequest to AstronomicalObjectRequestDTO"() {
		given:
		AstronomicalObjectRequest astronomicalObjectRequest = new AstronomicalObjectRequest(
				guid: GUID,
				name: NAME,
				astronomicalObjectType: SOAP_ASTRONOMICAL_OBJECT_TYPE,
				locationGuid: LOCATION_GUID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectSoapMapper.map astronomicalObjectRequest

		then:
		astronomicalObjectRequestDTO.guid == GUID
		astronomicalObjectRequestDTO.name == NAME
		astronomicalObjectRequestDTO.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectRequestDTO.locationGuid == LOCATION_GUID
	}

	void "maps DB entity to SOAP entity"() {
		given:
		DBAstronomicalObject dBAstronomicalObject = createAstronomicalObject()

		when:
		SOAPAstronomicalObject soapAstronomicalObject = astronomicalObjectSoapMapper.map(Lists.newArrayList(dBAstronomicalObject))[0]

		then:
		soapAstronomicalObject.guid == GUID
		soapAstronomicalObject.name == NAME
		soapAstronomicalObject.astronomicalObjectType == SOAP_ASTRONOMICAL_OBJECT_TYPE
		soapAstronomicalObject.locationHeader != null
	}

}

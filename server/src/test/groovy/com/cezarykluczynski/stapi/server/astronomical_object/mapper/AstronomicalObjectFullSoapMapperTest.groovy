package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import org.mapstruct.factory.Mappers

class AstronomicalObjectFullSoapMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper

	void setup() {
		astronomicalObjectFullSoapMapper = Mappers.getMapper(AstronomicalObjectFullSoapMapper)
	}

	void "maps SOAP AstronomicalObjectFullRequest to AstronomicalObjectBaseRequestDTO"() {
		given:
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = new AstronomicalObjectFullRequest(uid: UID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectFullSoapMapper.mapFull astronomicalObjectFullRequest

		then:
		astronomicalObjectRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		AstronomicalObject dBAstronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectFull astronomicalObjectFull = astronomicalObjectFullSoapMapper.mapFull(dBAstronomicalObject)

		then:
		astronomicalObjectFull.uid == UID
		astronomicalObjectFull.name == NAME
		astronomicalObjectFull.astronomicalObjectType == SOAP_ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectFull.location != null
	}

}

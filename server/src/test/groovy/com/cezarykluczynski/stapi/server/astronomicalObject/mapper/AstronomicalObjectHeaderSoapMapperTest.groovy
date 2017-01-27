package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectHeader
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectHeaderSoapMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectHeaderSoapMapper astronomicalObjectHeaderSoapMapper

	void setup() {
		astronomicalObjectHeaderSoapMapper = Mappers.getMapper(AstronomicalObjectHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		AstronomicalObject astronomicalObject = new AstronomicalObject(
				guid: GUID,
				name: NAME)

		when:
		AstronomicalObjectHeader astronomicalObjectHeader = astronomicalObjectHeaderSoapMapper.map(Lists.newArrayList(astronomicalObject))[0]

		then:
		astronomicalObjectHeader.guid == GUID
		astronomicalObjectHeader.name == NAME
	}

}

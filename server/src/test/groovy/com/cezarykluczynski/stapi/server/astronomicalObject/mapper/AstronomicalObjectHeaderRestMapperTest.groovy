package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectHeader
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectHeaderRestMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectHeaderRestMapper astronomicalObjectHeaderRestMapper

	void setup() {
		astronomicalObjectHeaderRestMapper = Mappers.getMapper(AstronomicalObjectHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		AstronomicalObject astronomicalObject = new AstronomicalObject(
				guid: GUID,
				name: NAME)

		when:
		AstronomicalObjectHeader astronomicalObjectHeader = astronomicalObjectHeaderRestMapper.map(Lists.newArrayList(astronomicalObject))[0]

		then:
		astronomicalObjectHeader.guid == GUID
		astronomicalObjectHeader.name == NAME
	}

}

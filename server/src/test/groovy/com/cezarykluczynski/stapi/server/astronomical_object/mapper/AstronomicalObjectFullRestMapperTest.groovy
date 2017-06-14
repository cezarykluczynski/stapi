package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import org.mapstruct.factory.Mappers

class AstronomicalObjectFullRestMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper

	void setup() {
		astronomicalObjectFullRestMapper = Mappers.getMapper(AstronomicalObjectFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		AstronomicalObject astronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectFull astronomicalObjectFull = astronomicalObjectFullRestMapper.mapFull(astronomicalObject)

		then:
		astronomicalObjectFull.uid == UID
		astronomicalObjectFull.name == NAME
		astronomicalObjectFull.astronomicalObjectType == REST_ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectFull.location != null
	}

}

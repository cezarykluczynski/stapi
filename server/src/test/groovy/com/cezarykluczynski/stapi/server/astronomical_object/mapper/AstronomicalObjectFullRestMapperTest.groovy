package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		AstronomicalObject astronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectV2Full astronomicalObjectV2Full = astronomicalObjectFullRestMapper.mapV2Full(astronomicalObject)

		then:
		astronomicalObjectV2Full.uid == UID
		astronomicalObjectV2Full.name == NAME
		astronomicalObjectV2Full.astronomicalObjectType == REST_ASTRONOMICAL_OBJECT_V2_TYPE
		astronomicalObjectV2Full.location != null
	}

}

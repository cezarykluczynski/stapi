package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObject as RESTAstronomicalObject
import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject as DBAstronomicalObject
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectRestMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectRestMapper astronomicalObjectRestMapper

	void setup() {
		astronomicalObjectRestMapper = Mappers.getMapper(AstronomicalObjectRestMapper)
	}

	void "maps AstronomicalObjectRestBeanParams to AstronomicalObjectRequestDTO"() {
		given:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams(
				guid: GUID,
				name: NAME,
				astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE,
				locationGuid: LOCATION_GUID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectRestMapper.map astronomicalObjectRestBeanParams

		then:
		astronomicalObjectRequestDTO.guid == GUID
		astronomicalObjectRequestDTO.name == NAME
		astronomicalObjectRequestDTO.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectRequestDTO.locationGuid == LOCATION_GUID
	}

	void "maps DB entity to REST entity"() {
		given:
		DBAstronomicalObject dBAstronomicalObject = createAstronomicalObject()

		when:
		RESTAstronomicalObject restAstronomicalObject = astronomicalObjectRestMapper.map(Lists.newArrayList(dBAstronomicalObject))[0]

		then:
		restAstronomicalObject.guid == GUID
		restAstronomicalObject.name == NAME
		restAstronomicalObject.astronomicalObjectType == REST_ASTRONOMICAL_OBJECT_TYPE
		restAstronomicalObject.locationHeader != null
	}

}

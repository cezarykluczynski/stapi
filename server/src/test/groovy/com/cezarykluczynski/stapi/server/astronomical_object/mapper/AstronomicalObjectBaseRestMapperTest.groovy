package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBase
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class AstronomicalObjectBaseRestMapperTest extends AbstractAstronomicalObjectMapperTest {

	private AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper

	void setup() {
		astronomicalObjectBaseRestMapper = Mappers.getMapper(AstronomicalObjectBaseRestMapper)
	}

	void "maps AstronomicalObjectRestBeanParams to AstronomicalObjectRequestDTO"() {
		given:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams(
				uid: UID,
				name: NAME,
				astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE,
				locationUid: LOCATION_UID)

		when:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = astronomicalObjectBaseRestMapper.mapBase astronomicalObjectRestBeanParams

		then:
		astronomicalObjectRequestDTO.uid == UID
		astronomicalObjectRequestDTO.name == NAME
		astronomicalObjectRequestDTO.astronomicalObjectType == ASTRONOMICAL_OBJECT_TYPE
		astronomicalObjectRequestDTO.locationUid == LOCATION_UID
	}

	void "maps DB entity to base REST entity"() {
		given:
		AstronomicalObject dBAstronomicalObject = createAstronomicalObject()

		when:
		AstronomicalObjectBase restAstronomicalObject = astronomicalObjectBaseRestMapper.mapBase(Lists.newArrayList(dBAstronomicalObject))[0]

		then:
		restAstronomicalObject.uid == UID
		restAstronomicalObject.name == NAME
		restAstronomicalObject.astronomicalObjectType == REST_ASTRONOMICAL_OBJECT_TYPE
		restAstronomicalObject.location != null
	}

}

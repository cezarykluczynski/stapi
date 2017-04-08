package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType as RestAstronomicalObjectType
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest

abstract class AbstractAstronomicalObjectMapperTest extends AbstractAstronomicalObjectTest {

	protected static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final RestAstronomicalObjectType REST_ASTRONOMICAL_OBJECT_TYPE = RestAstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final AstronomicalObjectTypeEnum SOAP_ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectTypeEnum.ARTIFICIAL_PLANET
	protected final AstronomicalObject location = Mock()

	protected AstronomicalObject createAstronomicalObject() {
		new AstronomicalObject(
				guid: GUID,
				name: NAME,
				astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE,
				location: location)
	}

}

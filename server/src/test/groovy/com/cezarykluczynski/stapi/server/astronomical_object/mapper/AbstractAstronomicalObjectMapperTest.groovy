package com.cezarykluczynski.stapi.server.astronomical_object.mapper

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType as RestAstronomicalObjectType
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type as RestAstronomicalObjectV2Type
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest

abstract class AbstractAstronomicalObjectMapperTest extends AbstractAstronomicalObjectTest {

	protected static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final RestAstronomicalObjectType REST_ASTRONOMICAL_OBJECT_TYPE = RestAstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final RestAstronomicalObjectV2Type REST_ASTRONOMICAL_OBJECT_V2_TYPE = RestAstronomicalObjectV2Type.ARTIFICIAL_PLANET
	protected final AstronomicalObject location = Mock()

	protected AstronomicalObject createAstronomicalObject() {
		new AstronomicalObject(
				uid: UID,
				name: NAME,
				astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE,
				location: location)
	}

}

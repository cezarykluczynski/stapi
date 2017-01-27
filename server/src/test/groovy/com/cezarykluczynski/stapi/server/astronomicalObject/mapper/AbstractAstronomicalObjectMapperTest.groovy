package com.cezarykluczynski.stapi.server.astronomicalObject.mapper

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType
import spock.lang.Specification
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectType as RestAstronomicalObjectType

abstract class AbstractAstronomicalObjectMapperTest extends Specification {

	protected static final String GUID = 'GUID'
	protected static final String NAME = 'NAME'
	protected static final String LOCATION_GUID = 'LOCATION_GUID'
	protected static final AstronomicalObjectType ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final RestAstronomicalObjectType REST_ASTRONOMICAL_OBJECT_TYPE = RestAstronomicalObjectType.ARTIFICIAL_PLANET
	protected static final AstronomicalObjectTypeEnum SOAP_ASTRONOMICAL_OBJECT_TYPE = AstronomicalObjectTypeEnum.ARTIFICIAL_PLANET
	protected final AstronomicalObject location = Mock(AstronomicalObject)

	protected AstronomicalObject createAstronomicalObject() {
		new AstronomicalObject(
				guid: GUID,
				name: NAME,
				astronomicalObjectType: ASTRONOMICAL_OBJECT_TYPE,
				location: location)
	}

}

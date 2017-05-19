package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LocationHeader
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LocationHeaderSoapMapperTest extends AbstractLocationMapperTest {

	private LocationHeaderSoapMapper locationHeaderSoapMapper

	void setup() {
		locationHeaderSoapMapper = Mappers.getMapper(LocationHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Location location = new Location(
				uid: UID,
				name: NAME)

		when:
		LocationHeader locationHeader = locationHeaderSoapMapper.map(Lists.newArrayList(location))[0]

		then:
		locationHeader.uid == UID
		locationHeader.name == NAME
	}

}

package com.cezarykluczynski.stapi.server.location.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationHeader
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LocationHeaderRestMapperTest extends AbstractLocationMapperTest {

	private LocationHeaderRestMapper locationHeaderRestMapper

	void setup() {
		locationHeaderRestMapper = Mappers.getMapper(LocationHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Location location = new Location(
				uid: UID,
				name: NAME)

		when:
		LocationHeader locationHeader = locationHeaderRestMapper.map(Lists.newArrayList(location))[0]

		then:
		locationHeader.uid == UID
		locationHeader.name == NAME
	}

}

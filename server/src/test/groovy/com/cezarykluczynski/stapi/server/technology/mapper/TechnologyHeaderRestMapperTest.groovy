package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyHeader
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TechnologyHeaderRestMapperTest extends AbstractTechnologyMapperTest {

	private TechnologyHeaderRestMapper technologyHeaderRestMapper

	void setup() {
		technologyHeaderRestMapper = Mappers.getMapper(TechnologyHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Technology technology = new Technology(
				uid: UID,
				name: NAME)

		when:
		TechnologyHeader technologyHeader = technologyHeaderRestMapper.map(Lists.newArrayList(technology))[0]

		then:
		technologyHeader.uid == UID
		technologyHeader.name == NAME
	}

}

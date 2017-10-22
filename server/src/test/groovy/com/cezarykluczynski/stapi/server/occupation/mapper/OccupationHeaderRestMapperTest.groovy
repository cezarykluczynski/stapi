package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationHeader
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OccupationHeaderRestMapperTest extends AbstractOccupationMapperTest {

	private OccupationHeaderRestMapper occupationHeaderRestMapper

	void setup() {
		occupationHeaderRestMapper = Mappers.getMapper(OccupationHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Occupation occupation = new Occupation(
				uid: UID,
				name: NAME)

		when:
		OccupationHeader occupationHeader = occupationHeaderRestMapper.map(Lists.newArrayList(occupation))[0]

		then:
		occupationHeader.uid == UID
		occupationHeader.name == NAME
	}

}

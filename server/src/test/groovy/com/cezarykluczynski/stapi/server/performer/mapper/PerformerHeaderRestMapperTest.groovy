package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerHeader
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerHeaderRestMapperTest extends AbstractRealWorldPersonMapperTest {

	private PerformerHeaderRestMapper performerHeaderRestMapper

	void setup() {
		performerHeaderRestMapper = Mappers.getMapper(PerformerHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Performer performer = new Performer(
				name: NAME,
				uid: UID)

		when:
		PerformerHeader performerHeader = performerHeaderRestMapper.map(Lists.newArrayList(performer))[0]

		then:
		performerHeader.name == NAME
		performerHeader.uid == UID
	}

}

package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictHeader
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ConflictHeaderRestMapperTest extends AbstractConflictMapperTest {

	private ConflictHeaderRestMapper conflictHeaderRestMapper

	void setup() {
		conflictHeaderRestMapper = Mappers.getMapper(ConflictHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Conflict conflict = new Conflict(
				uid: UID,
				name: NAME)

		when:
		ConflictHeader conflictHeader = conflictHeaderRestMapper.map(Lists.newArrayList(conflict))[0]

		then:
		conflictHeader.uid == UID
		conflictHeader.name == NAME
	}

}

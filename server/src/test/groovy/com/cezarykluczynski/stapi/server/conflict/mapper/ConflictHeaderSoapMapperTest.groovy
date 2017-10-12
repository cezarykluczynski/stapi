package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ConflictHeader
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ConflictHeaderSoapMapperTest extends AbstractConflictMapperTest {

	private ConflictHeaderSoapMapper conflictHeaderSoapMapper

	void setup() {
		conflictHeaderSoapMapper = Mappers.getMapper(ConflictHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Conflict conflict = new Conflict(
				uid: UID,
				name: NAME)

		when:
		ConflictHeader conflictHeader = conflictHeaderSoapMapper.map(Lists.newArrayList(conflict))[0]

		then:
		conflictHeader.uid == UID
		conflictHeader.name == NAME
	}

}

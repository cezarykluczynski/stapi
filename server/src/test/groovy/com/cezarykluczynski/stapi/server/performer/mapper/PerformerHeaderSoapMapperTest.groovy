package com.cezarykluczynski.stapi.server.performer.mapper

import com.cezarykluczynski.stapi.client.v1.soap.PerformerHeader
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class PerformerHeaderSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private PerformerHeaderSoapMapper performerHeaderSoapMapper

	def setup() {
		performerHeaderSoapMapper = Mappers.getMapper(PerformerHeaderSoapMapper)
	}

	def "maps DB entity to SOAP header"() {
		given:
		Performer performer = new Performer(
				name: NAME,
				guid: GUID)

		when:
		PerformerHeader performerHeader = performerHeaderSoapMapper.map(Lists.newArrayList(performer))[0]

		then:
		performerHeader.name == NAME
		performerHeader.guid == GUID
	}

}

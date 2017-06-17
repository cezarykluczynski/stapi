package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureHeader
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LiteratureHeaderSoapMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureHeaderSoapMapper literatureHeaderSoapMapper

	void setup() {
		literatureHeaderSoapMapper = Mappers.getMapper(LiteratureHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Literature literature = new Literature(
				uid: UID,
				title: TITLE)

		when:
		LiteratureHeader literatureHeader = literatureHeaderSoapMapper.map(Lists.newArrayList(literature))[0]

		then:
		literatureHeader.uid == UID
		literatureHeader.title == TITLE
	}

}

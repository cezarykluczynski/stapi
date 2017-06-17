package com.cezarykluczynski.stapi.server.literature.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureHeader
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class LiteratureHeaderRestMapperTest extends AbstractLiteratureMapperTest {

	private LiteratureHeaderRestMapper literatureHeaderRestMapper

	void setup() {
		literatureHeaderRestMapper = Mappers.getMapper(LiteratureHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Literature literature = new Literature(
				uid: UID,
				title: TITLE)

		when:
		LiteratureHeader literatureHeader = literatureHeaderRestMapper.map(Lists.newArrayList(literature))[0]

		then:
		literatureHeader.uid == UID
		literatureHeader.title == TITLE
	}

}

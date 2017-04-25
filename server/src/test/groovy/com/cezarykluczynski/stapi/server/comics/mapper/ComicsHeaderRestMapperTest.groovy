package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsHeader
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsHeaderRestMapperTest extends AbstractComicsMapperTest {

	private ComicsHeaderRestMapper comicsHeaderRestMapper

	void setup() {
		comicsHeaderRestMapper = Mappers.getMapper(ComicsHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Comics comics = new Comics(
				uid: UID,
				title: TITLE)

		when:
		ComicsHeader comicsHeader = comicsHeaderRestMapper.map(Lists.newArrayList(comics))[0]

		then:
		comicsHeader.uid == UID
		comicsHeader.title == TITLE
	}

}

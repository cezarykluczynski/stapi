package com.cezarykluczynski.stapi.server.comics.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicsHeader
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicsHeaderSoapMapperTest extends AbstractComicsMapperTest {

	private ComicsHeaderSoapMapper comicsHeaderSoapMapper

	void setup() {
		comicsHeaderSoapMapper = Mappers.getMapper(ComicsHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Comics comics = new Comics(
				uid: UID,
				title: TITLE)

		when:
		ComicsHeader comicsHeader = comicsHeaderSoapMapper.map(Lists.newArrayList(comics))[0]

		then:
		comicsHeader.uid == UID
		comicsHeader.title == TITLE
	}

}

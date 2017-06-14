package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripHeader
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripHeaderSoapMapperTest extends AbstractComicStripMapperTest {

	private ComicStripHeaderSoapMapper comicStripHeaderSoapMapper

	void setup() {
		comicStripHeaderSoapMapper = Mappers.getMapper(ComicStripHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		ComicStrip comicStrip = new ComicStrip(
				uid: UID,
				title: TITLE)

		when:
		ComicStripHeader comicStripHeader = comicStripHeaderSoapMapper.map(Lists.newArrayList(comicStrip))[0]

		then:
		comicStripHeader.uid == UID
		comicStripHeader.title == TITLE
	}

}

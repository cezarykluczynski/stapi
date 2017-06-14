package com.cezarykluczynski.stapi.server.comic_strip.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripHeader
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicStripHeaderRestMapperTest extends AbstractComicStripMapperTest {

	private ComicStripHeaderRestMapper comicStripHeaderRestMapper

	void setup() {
		comicStripHeaderRestMapper = Mappers.getMapper(ComicStripHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		ComicStrip comicStrip = new ComicStrip(
				uid: UID,
				title: TITLE)

		when:
		ComicStripHeader comicStripHeader = comicStripHeaderRestMapper.map(Lists.newArrayList(comicStrip))[0]

		then:
		comicStripHeader.uid == UID
		comicStripHeader.title == TITLE
	}

}

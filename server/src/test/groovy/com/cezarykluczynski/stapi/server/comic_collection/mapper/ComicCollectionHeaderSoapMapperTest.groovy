package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionHeader
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionHeaderSoapMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionHeaderSoapMapper comicCollectionHeaderSoapMapper

	void setup() {
		comicCollectionHeaderSoapMapper = Mappers.getMapper(ComicCollectionHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		ComicCollection comicCollection = new ComicCollection(
				uid: UID,
				title: TITLE)

		when:
		ComicCollectionHeader comicCollectionHeader = comicCollectionHeaderSoapMapper.map(Lists.newArrayList(comicCollection))[0]

		then:
		comicCollectionHeader.uid == UID
		comicCollectionHeader.title == TITLE
	}

}

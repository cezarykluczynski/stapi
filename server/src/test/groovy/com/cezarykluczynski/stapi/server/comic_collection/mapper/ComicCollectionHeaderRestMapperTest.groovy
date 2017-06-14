package com.cezarykluczynski.stapi.server.comic_collection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionHeader
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicCollectionHeaderRestMapperTest extends AbstractComicCollectionMapperTest {

	private ComicCollectionHeaderRestMapper comicCollectionHeaderRestMapper

	void setup() {
		comicCollectionHeaderRestMapper = Mappers.getMapper(ComicCollectionHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		ComicCollection comicCollection = new ComicCollection(
				uid: UID,
				title: TITLE)

		when:
		ComicCollectionHeader comicCollectionHeader = comicCollectionHeaderRestMapper.map(Lists.newArrayList(comicCollection))[0]

		then:
		comicCollectionHeader.uid == UID
		comicCollectionHeader.title == TITLE
	}

}

package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionHeader
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookCollectionHeaderRestMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionHeaderRestMapper bookCollectionHeaderRestMapper

	void setup() {
		bookCollectionHeaderRestMapper = Mappers.getMapper(BookCollectionHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		BookCollection bookCollection = new BookCollection(
				uid: UID,
				title: TITLE)

		when:
		BookCollectionHeader bookCollectionHeader = bookCollectionHeaderRestMapper.map(Lists.newArrayList(bookCollection))[0]

		then:
		bookCollectionHeader.uid == UID
		bookCollectionHeader.title == TITLE
	}

}

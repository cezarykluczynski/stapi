package com.cezarykluczynski.stapi.server.book_collection.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionHeader
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookCollectionHeaderSoapMapperTest extends AbstractBookCollectionMapperTest {

	private BookCollectionHeaderSoapMapper bookCollectionHeaderSoapMapper

	void setup() {
		bookCollectionHeaderSoapMapper = Mappers.getMapper(BookCollectionHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		BookCollection bookCollection = new BookCollection(
				uid: UID,
				title: TITLE)

		when:
		BookCollectionHeader bookCollectionHeader = bookCollectionHeaderSoapMapper.map(Lists.newArrayList(bookCollection))[0]

		then:
		bookCollectionHeader.uid == UID
		bookCollectionHeader.title == TITLE
	}

}

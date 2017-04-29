package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.BookHeader
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookHeaderRestMapperTest extends AbstractBookMapperTest {

	private BookHeaderRestMapper bookHeaderRestMapper

	void setup() {
		bookHeaderRestMapper = Mappers.getMapper(BookHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Book book = new Book(
				uid: UID,
				title: TITLE)

		when:
		BookHeader bookHeader = bookHeaderRestMapper.map(Lists.newArrayList(book))[0]

		then:
		bookHeader.uid == UID
		bookHeader.title == TITLE
	}

}

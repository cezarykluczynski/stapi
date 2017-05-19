package com.cezarykluczynski.stapi.server.book.mapper

import com.cezarykluczynski.stapi.client.v1.soap.BookHeader
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class BookHeaderSoapMapperTest extends AbstractBookMapperTest {

	private BookHeaderSoapMapper bookHeaderSoapMapper

	void setup() {
		bookHeaderSoapMapper = Mappers.getMapper(BookHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Book book = new Book(
				uid: UID,
				title: TITLE)

		when:
		BookHeader bookHeader = bookHeaderSoapMapper.map(Lists.newArrayList(book))[0]

		then:
		bookHeader.uid == UID
		bookHeader.title == TITLE
	}

}

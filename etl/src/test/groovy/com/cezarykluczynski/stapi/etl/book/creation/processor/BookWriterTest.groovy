package com.cezarykluczynski.stapi.etl.book.creation.processor

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book.repository.BookRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class BookWriterTest extends Specification {

	private BookRepository bookRepositoryMock

	private BookWriter bookWriterMock

	void setup() {
		bookRepositoryMock = Mock()
		bookWriterMock = new BookWriter(bookRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Book book = new Book()
		List<Book> bookList = Lists.newArrayList(book)

		when:
		bookWriterMock.write(new Chunk(bookList))

		then:
		1 * bookRepositoryMock.saveAll(bookList)
		0 * _
	}

}

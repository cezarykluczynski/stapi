package com.cezarykluczynski.stapi.etl.book.creation.processor

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.book.repository.BookRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class BookWriterTest extends Specification {

	private BookRepository bookRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private BookWriter bookWriterMock

	void setup() {
		bookRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		bookWriterMock = new BookWriter(bookRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Book book = new Book()
		List<Book> bookList = Lists.newArrayList(book)

		when:
		bookWriterMock.write(bookList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Book) >> { args ->
			assert args[0][0] == book
			bookList
		}
		1 * bookRepositoryMock.save(bookList)
		0 * _
	}

}

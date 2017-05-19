package com.cezarykluczynski.stapi.server.book.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBase
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFull
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper
import com.cezarykluczynski.stapi.server.book.query.BookRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookRestReaderTest extends Specification {

	private static final String UID = 'UID'

	private BookRestQuery bookRestQueryBuilderMock

	private BookBaseRestMapper bookBaseRestMapperMock

	private BookFullRestMapper bookFullRestMapperMock

	private PageMapper pageMapperMock

	private BookRestReader bookRestReader

	void setup() {
		bookRestQueryBuilderMock = Mock()
		bookBaseRestMapperMock = Mock()
		bookFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookRestReader = new BookRestReader(bookRestQueryBuilderMock, bookBaseRestMapperMock, bookFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookBase bookBase = Mock()
		Book book = Mock()
		BookRestBeanParams bookRestBeanParams = Mock()
		List<BookBase> restBookList = Lists.newArrayList(bookBase)
		List<Book> bookList = Lists.newArrayList(book)
		Page<Book> bookPage = Mock()
		ResponsePage responsePage = Mock()

		when:
		BookBaseResponse bookResponseOutput = bookRestReader.readBase(bookRestBeanParams)

		then:
		1 * bookRestQueryBuilderMock.query(bookRestBeanParams) >> bookPage
		1 * pageMapperMock.fromPageToRestResponsePage(bookPage) >> responsePage
		1 * bookPage.content >> bookList
		1 * bookBaseRestMapperMock.mapBase(bookList) >> restBookList
		0 * _
		bookResponseOutput.books == restBookList
		bookResponseOutput.page == responsePage
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		BookFull bookFull = Mock()
		Book book = Mock()
		List<Book> bookList = Lists.newArrayList(book)
		Page<Book> bookPage = Mock()

		when:
		BookFullResponse bookResponseOutput = bookRestReader.readFull(UID)

		then:
		1 * bookRestQueryBuilderMock.query(_ as BookRestBeanParams) >> { BookRestBeanParams bookRestBeanParams ->
			assert bookRestBeanParams.uid == UID
			bookPage
		}
		1 * bookPage.content >> bookList
		1 * bookFullRestMapperMock.mapFull(book) >> bookFull
		0 * _
		bookResponseOutput.book == bookFull
	}

	void "requires UID in full request"() {
		when:
		bookRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}

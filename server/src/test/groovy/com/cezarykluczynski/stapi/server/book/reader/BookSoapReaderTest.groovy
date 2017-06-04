package com.cezarykluczynski.stapi.server.book.reader

import com.cezarykluczynski.stapi.client.v1.soap.BookBase
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookFull
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper
import com.cezarykluczynski.stapi.server.book.query.BookSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private BookSoapQuery bookSoapQueryBuilderMock

	private BookBaseSoapMapper bookBaseSoapMapperMock

	private BookFullSoapMapper bookFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private BookSoapReader bookSoapReader

	void setup() {
		bookSoapQueryBuilderMock = Mock()
		bookBaseSoapMapperMock = Mock()
		bookFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		bookSoapReader = new BookSoapReader(bookSoapQueryBuilderMock, bookBaseSoapMapperMock, bookFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Book> bookList = Lists.newArrayList()
		Page<Book> bookPage = Mock()
		List<BookBase> soapBookList = Lists.newArrayList(new BookBase(uid: UID))
		BookBaseRequest bookBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		BookBaseResponse bookResponse = bookSoapReader.readBase(bookBaseRequest)

		then:
		1 * bookSoapQueryBuilderMock.query(bookBaseRequest) >> bookPage
		1 * bookPage.content >> bookList
		1 * pageMapperMock.fromPageToSoapResponsePage(bookPage) >> responsePage
		1 * bookBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * bookBaseSoapMapperMock.mapBase(bookList) >> soapBookList
		0 * _
		bookResponse.books[0].uid == UID
		bookResponse.page == responsePage
		bookResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookFull bookFull = new BookFull(uid: UID)
		Book book = Mock()
		Page<Book> bookPage = Mock()
		BookFullRequest bookFullRequest = new BookFullRequest(uid: UID)

		when:
		BookFullResponse bookFullResponse = bookSoapReader.readFull(bookFullRequest)

		then:
		1 * bookSoapQueryBuilderMock.query(bookFullRequest) >> bookPage
		1 * bookPage.content >> Lists.newArrayList(book)
		1 * bookFullSoapMapperMock.mapFull(book) >> bookFull
		0 * _
		bookFullResponse.book.uid == UID
	}

	void "requires UID in full request"() {
		given:
		BookFullRequest bookFullRequest = Mock()

		when:
		bookSoapReader.readFull(bookFullRequest)

		then:
		thrown(MissingUIDException)
	}

}

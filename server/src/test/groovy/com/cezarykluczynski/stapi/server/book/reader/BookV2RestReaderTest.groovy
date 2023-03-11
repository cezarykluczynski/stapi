package com.cezarykluczynski.stapi.server.book.reader

import com.cezarykluczynski.stapi.client.rest.model.BookV2Base
import com.cezarykluczynski.stapi.client.rest.model.BookV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookV2Full
import com.cezarykluczynski.stapi.client.rest.model.BookV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.server.book.dto.BookV2RestBeanParams
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper
import com.cezarykluczynski.stapi.server.book.query.BookRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private BookRestQuery bookRestQueryBuilderMock

	private BookBaseRestMapper bookBaseRestMapperMock

	private BookFullRestMapper bookFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private BookV2RestReader bookV2RestReader

	void setup() {
		bookRestQueryBuilderMock = Mock()
		bookBaseRestMapperMock = Mock()
		bookFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		bookV2RestReader = new BookV2RestReader(bookRestQueryBuilderMock, bookBaseRestMapperMock, bookFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookV2Base bookV2Base = Mock()
		Book book = Mock()
		BookV2RestBeanParams bookV2RestBeanParams = Mock()
		List<BookV2Base> restBookList = Lists.newArrayList(bookV2Base)
		List<Book> bookList = Lists.newArrayList(book)
		Page<Book> bookPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		BookV2BaseResponse bookV2ResponseOutput = bookV2RestReader.readBase(bookV2RestBeanParams)

		then:
		1 * bookRestQueryBuilderMock.query(bookV2RestBeanParams) >> bookPage
		1 * pageMapperMock.fromPageToRestResponsePage(bookPage) >> responsePage
		1 * bookV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * bookPage.content >> bookList
		1 * bookBaseRestMapperMock.mapV2Base(bookList) >> restBookList
		0 * _
		bookV2ResponseOutput.books == restBookList
		bookV2ResponseOutput.page == responsePage
		bookV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		BookV2Full bookV2Full = Mock()
		Book book = Mock()
		List<Book> bookList = Lists.newArrayList(book)
		Page<Book> bookPage = Mock()

		when:
		BookV2FullResponse bookResponseOutput = bookV2RestReader.readFull(UID)

		then:
		1 * bookRestQueryBuilderMock.query(_ as BookV2RestBeanParams) >> { BookV2RestBeanParams bookRestBeanParams ->
				assert bookRestBeanParams.uid == UID
				bookPage
		}
		1 * bookPage.content >> bookList
		1 * bookFullRestMapperMock.mapV2Full(book) >> bookV2Full
		0 * _
		bookResponseOutput.book == bookV2Full
	}

	void "requires UID in full request"() {
		when:
		bookV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}

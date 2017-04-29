package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams
import com.cezarykluczynski.stapi.server.book.reader.BookRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class BookRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private BookRestReader bookRestReaderMock

	private BookRestEndpoint bookRestEndpoint

	void setup() {
		bookRestReaderMock = Mock()
		bookRestEndpoint = new BookRestEndpoint(bookRestReaderMock)
	}

	void "passes get call to BookRestReader"() {
		given:
		BookFullResponse bookFullResponse = Mock()

		when:
		BookFullResponse bookFullResponseOutput = bookRestEndpoint.getBook(UID)

		then:
		1 * bookRestReaderMock.readFull(UID) >> bookFullResponse
		bookFullResponseOutput == bookFullResponse
	}

	void "passes search get call to BookRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		BookBaseResponse bookResponse = Mock()

		when:
		BookBaseResponse bookResponseOutput = bookRestEndpoint.searchBook(pageAwareBeanParams)

		then:
		1 * bookRestReaderMock.readBase(_ as BookRestBeanParams) >> { BookRestBeanParams bookRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			bookResponse
		}
		bookResponseOutput == bookResponse
	}

	void "passes search post call to BookRestReader"() {
		given:
		BookRestBeanParams bookRestBeanParams = new BookRestBeanParams(title: TITLE)
		BookBaseResponse bookResponse = Mock()

		when:
		BookBaseResponse bookResponseOutput = bookRestEndpoint.searchBook(bookRestBeanParams)

		then:
		1 * bookRestReaderMock.readBase(bookRestBeanParams as BookRestBeanParams) >> { BookRestBeanParams params ->
			assert params.title == TITLE
			bookResponse
		}
		bookResponseOutput == bookResponse
	}

}

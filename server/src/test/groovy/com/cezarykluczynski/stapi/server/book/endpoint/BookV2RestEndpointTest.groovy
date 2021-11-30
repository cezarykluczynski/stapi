package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse
import com.cezarykluczynski.stapi.server.book.dto.BookV2RestBeanParams
import com.cezarykluczynski.stapi.server.book.reader.BookV2RestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class BookV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private BookV2RestReader bookV2RestReaderMock

	private BookV2RestEndpoint bookV2RestEndpoint

	void setup() {
		bookV2RestReaderMock = Mock()
		bookV2RestEndpoint = new BookV2RestEndpoint(bookV2RestReaderMock)
	}

	void "passes get call to BookRestReader"() {
		given:
		BookV2FullResponse bookV2FullResponse = Mock()

		when:
		BookV2FullResponse bookV2FullResponseOutput = bookV2RestEndpoint.getBook(UID)

		then:
		1 * bookV2RestReaderMock.readFull(UID) >> bookV2FullResponse
		bookV2FullResponseOutput == bookV2FullResponse
	}

	void "passes search get call to BookRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		BookV2BaseResponse bookV2Response = Mock()

		when:
		BookV2BaseResponse bookV2ResponseOutput = bookV2RestEndpoint
				.searchBook(pageAwareBeanParams)

		then:
		1 * bookV2RestReaderMock.readBase(_ as BookV2RestBeanParams) >> { BookV2RestBeanParams bookRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			bookV2Response
		}
		bookV2ResponseOutput == bookV2Response
	}

	void "passes search post call to BookRestReader"() {
		given:
		BookV2RestBeanParams bookV2RestBeanParams = new BookV2RestBeanParams(title: TITLE)
		BookV2BaseResponse bookV2Response = Mock()

		when:
		BookV2BaseResponse bookV2ResponseOutput = bookV2RestEndpoint.searchBook(bookV2RestBeanParams)

		then:
		1 * bookV2RestReaderMock.readBase(bookV2RestBeanParams as BookV2RestBeanParams) >> {
				BookV2RestBeanParams params ->
			assert params.title == TITLE
			bookV2Response
		}
		bookV2ResponseOutput == bookV2Response
	}

}

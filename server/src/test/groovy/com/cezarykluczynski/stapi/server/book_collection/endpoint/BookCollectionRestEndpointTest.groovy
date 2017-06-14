package com.cezarykluczynski.stapi.server.book_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.book_collection.reader.BookCollectionRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class BookCollectionRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private BookCollectionRestReader bookCollectionRestReaderMock

	private BookCollectionRestEndpoint bookCollectionRestEndpoint

	void setup() {
		bookCollectionRestReaderMock = Mock()
		bookCollectionRestEndpoint = new BookCollectionRestEndpoint(bookCollectionRestReaderMock)
	}

	void "passes get call to BookCollectionRestReader"() {
		given:
		BookCollectionFullResponse bookCollectionFullResponse = Mock()

		when:
		BookCollectionFullResponse bookCollectionFullResponseOutput = bookCollectionRestEndpoint.getBookCollection(UID)

		then:
		1 * bookCollectionRestReaderMock.readFull(UID) >> bookCollectionFullResponse
		bookCollectionFullResponseOutput == bookCollectionFullResponse
	}

	void "passes search get call to BookCollectionRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		BookCollectionBaseResponse bookCollectionResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionResponseOutput = bookCollectionRestEndpoint.searchBookCollection(pageAwareBeanParams)

		then:
		1 * bookCollectionRestReaderMock.readBase(_ as BookCollectionRestBeanParams) >> {
				BookCollectionRestBeanParams bookCollectionRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			bookCollectionResponse
		}
		bookCollectionResponseOutput == bookCollectionResponse
	}

	void "passes search post call to BookCollectionRestReader"() {
		given:
		BookCollectionRestBeanParams bookCollectionRestBeanParams = new BookCollectionRestBeanParams(title: TITLE)
		BookCollectionBaseResponse bookCollectionResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionResponseOutput = bookCollectionRestEndpoint.searchBookCollection(bookCollectionRestBeanParams)

		then:
		1 * bookCollectionRestReaderMock.readBase(bookCollectionRestBeanParams as BookCollectionRestBeanParams) >> {
				BookCollectionRestBeanParams params ->
			assert params.title == TITLE
			bookCollectionResponse
		}
		bookCollectionResponseOutput == bookCollectionResponse
	}

}

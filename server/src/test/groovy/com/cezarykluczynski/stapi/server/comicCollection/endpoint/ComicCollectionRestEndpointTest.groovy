package com.cezarykluczynski.stapi.server.comicCollection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionRestReader

class ComicCollectionRestEndpointTest extends AbstractRestEndpointTest {

	private ComicCollectionRestReader comicCollectionRestReaderMock

	private ComicCollectionRestEndpoint comicCollectionRestEndpoint

	void setup() {
		comicCollectionRestReaderMock = Mock(ComicCollectionRestReader)
		comicCollectionRestEndpoint = new ComicCollectionRestEndpoint(comicCollectionRestReaderMock)
	}

	void "passes get call to ComicCollectionRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicCollectionResponse comicCollectionResponse = Mock(ComicCollectionResponse)

		when:
		ComicCollectionResponse comicCollectionResponseOutput = comicCollectionRestEndpoint.getComicCollections(pageAwareBeanParams)

		then:
		1 * comicCollectionRestReaderMock.read(_ as ComicCollectionRestBeanParams) >> { ComicCollectionRestBeanParams comicCollectionRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicCollectionResponse
		}
		comicCollectionResponseOutput == comicCollectionResponse
	}

	void "passes post call to ComicCollectionRestReader"() {
		given:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams()
		ComicCollectionResponse comicCollectionResponse = Mock(ComicCollectionResponse)

		when:
		ComicCollectionResponse comicCollectionResponseOutput = comicCollectionRestEndpoint.searchComicCollections(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestReaderMock.read(comicCollectionRestBeanParams as ComicCollectionRestBeanParams) >> { ComicCollectionRestBeanParams params ->
			comicCollectionResponse
		}
		comicCollectionResponseOutput == comicCollectionResponse
	}

}

package com.cezarykluczynski.stapi.server.comicCollection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class ComicCollectionRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String TITLE = 'TITLE'

	private ComicCollectionRestReader comicCollectionRestReaderMock

	private ComicCollectionRestEndpoint comicCollectionRestEndpoint

	void setup() {
		comicCollectionRestReaderMock = Mock(ComicCollectionRestReader)
		comicCollectionRestEndpoint = new ComicCollectionRestEndpoint(comicCollectionRestReaderMock)
	}

	void "passes get call to ComicCollectionRestReader"() {
		given:
		ComicCollectionFullResponse comicCollectionFullResponse = Mock(ComicCollectionFullResponse)

		when:
		ComicCollectionFullResponse comicCollectionFullResponseOutput = comicCollectionRestEndpoint.getComicCollection(GUID)

		then:
		1 * comicCollectionRestReaderMock.readFull(GUID) >> comicCollectionFullResponse
		comicCollectionFullResponseOutput == comicCollectionFullResponse
	}

	void "passes search get call to ComicCollectionRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicCollectionBaseResponse comicCollectionResponse = Mock(ComicCollectionBaseResponse)

		when:
		ComicCollectionBaseResponse comicCollectionResponseOutput = comicCollectionRestEndpoint.searchComicCollection(pageAwareBeanParams)

		then:
		1 * comicCollectionRestReaderMock.readBase(_ as ComicCollectionRestBeanParams) >> {
				ComicCollectionRestBeanParams comicCollectionRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicCollectionResponse
		}
		comicCollectionResponseOutput == comicCollectionResponse
	}

	void "passes search post call to ComicCollectionRestReader"() {
		given:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams(title: TITLE)
		ComicCollectionBaseResponse comicCollectionResponse = Mock(ComicCollectionBaseResponse)

		when:
		ComicCollectionBaseResponse comicCollectionResponseOutput = comicCollectionRestEndpoint.searchComicCollection(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestReaderMock.readBase(comicCollectionRestBeanParams as ComicCollectionRestBeanParams) >> {
				ComicCollectionRestBeanParams params ->
			assert params.title == TITLE
			comicCollectionResponse
		}
		comicCollectionResponseOutput == comicCollectionResponse
	}

}

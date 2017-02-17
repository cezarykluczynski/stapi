package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.cezarykluczynski.stapi.server.comics.reader.ComicsRestReader

class ComicsRestEndpointTest extends AbstractRestEndpointTest {

	private ComicsRestReader comicsRestReaderMock

	private ComicsRestEndpoint comicsRestEndpoint

	void setup() {
		comicsRestReaderMock = Mock(ComicsRestReader)
		comicsRestEndpoint = new ComicsRestEndpoint(comicsRestReaderMock)
	}

	void "passes get call to ComicsRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicsResponse comicsResponse = Mock(ComicsResponse)

		when:
		ComicsResponse comicsResponseOutput = comicsRestEndpoint.getComics(pageAwareBeanParams)

		then:
		1 * comicsRestReaderMock.read(_ as ComicsRestBeanParams) >> { ComicsRestBeanParams comicsRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicsResponse
		}
		comicsResponseOutput == comicsResponse
	}

	void "passes post call to ComicsRestReader"() {
		given:
		ComicsRestBeanParams comicsRestBeanParams = new ComicsRestBeanParams()
		ComicsResponse comicsResponse = Mock(ComicsResponse)

		when:
		ComicsResponse comicsResponseOutput = comicsRestEndpoint.searchComics(comicsRestBeanParams)

		then:
		1 * comicsRestReaderMock.read(comicsRestBeanParams as ComicsRestBeanParams) >> { ComicsRestBeanParams params ->
			comicsResponse
		}
		comicsResponseOutput == comicsResponse
	}

}

package com.cezarykluczynski.stapi.server.comicSeries.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesRestReader

class ComicSeriesRestEndpointTest extends AbstractRestEndpointTest {

	private ComicSeriesRestReader comicSeriesRestReaderMock

	private ComicSeriesRestEndpoint comicSeriesRestEndpoint

	void setup() {
		comicSeriesRestReaderMock = Mock(ComicSeriesRestReader)
		comicSeriesRestEndpoint = new ComicSeriesRestEndpoint(comicSeriesRestReaderMock)
	}

	void "passes get call to ComicSeriesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicSeriesResponse comicSeriesResponse = Mock(ComicSeriesResponse)

		when:
		ComicSeriesResponse comicSeriesResponseOutput = comicSeriesRestEndpoint.getComicSeries(pageAwareBeanParams)

		then:
		1 * comicSeriesRestReaderMock.readBase(_ as ComicSeriesRestBeanParams) >> { ComicSeriesRestBeanParams comicSeriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicSeriesResponse
		}
		comicSeriesResponseOutput == comicSeriesResponse
	}

	void "passes post call to ComicSeriesRestReader"() {
		given:
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams()
		ComicSeriesResponse comicSeriesResponse = Mock(ComicSeriesResponse)

		when:
		ComicSeriesResponse comicSeriesResponseOutput = comicSeriesRestEndpoint.searchComicSeries(comicSeriesRestBeanParams)

		then:
		1 * comicSeriesRestReaderMock.readBase(comicSeriesRestBeanParams as ComicSeriesRestBeanParams) >> { ComicSeriesRestBeanParams params ->
			comicSeriesResponse
		}
		comicSeriesResponseOutput == comicSeriesResponse
	}

}

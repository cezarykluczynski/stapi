package com.cezarykluczynski.stapi.server.comicSeries.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class ComicSeriesRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String TITLE = 'TITLE'

	private ComicSeriesRestReader comicSeriesRestReaderMock

	private ComicSeriesRestEndpoint comicSeriesRestEndpoint

	void setup() {
		comicSeriesRestReaderMock = Mock(ComicSeriesRestReader)
		comicSeriesRestEndpoint = new ComicSeriesRestEndpoint(comicSeriesRestReaderMock)
	}

	void "passes get call to ComicSeriesRestReader"() {
		given:
		ComicSeriesFullResponse comicSeriesFullResponse = Mock(ComicSeriesFullResponse)

		when:
		ComicSeriesFullResponse comicSeriesFullResponseOutput = comicSeriesRestEndpoint.getComicSeries(GUID)

		then:
		1 * comicSeriesRestReaderMock.readFull(GUID) >> comicSeriesFullResponse
		comicSeriesFullResponseOutput == comicSeriesFullResponse
	}

	void "passes search get call to ComicSeriesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		ComicSeriesBaseResponse comicSeriesResponse = Mock(ComicSeriesBaseResponse)

		when:
		ComicSeriesBaseResponse comicSeriesResponseOutput = comicSeriesRestEndpoint.searchComicSeries(pageAwareBeanParams)

		then:
		1 * comicSeriesRestReaderMock.readBase(_ as ComicSeriesRestBeanParams) >> { ComicSeriesRestBeanParams comicSeriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			comicSeriesResponse
		}
		comicSeriesResponseOutput == comicSeriesResponse
	}

	void "passes search post call to ComicSeriesRestReader"() {
		given:
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams(title: TITLE)
		ComicSeriesBaseResponse comicSeriesResponse = Mock(ComicSeriesBaseResponse)

		when:
		ComicSeriesBaseResponse comicSeriesResponseOutput = comicSeriesRestEndpoint.searchComicSeries(comicSeriesRestBeanParams)

		then:
		1 * comicSeriesRestReaderMock.readBase(comicSeriesRestBeanParams as ComicSeriesRestBeanParams) >> { ComicSeriesRestBeanParams params ->
			assert params.title == TITLE
			comicSeriesResponse
		}
		comicSeriesResponseOutput == comicSeriesResponse
	}

}

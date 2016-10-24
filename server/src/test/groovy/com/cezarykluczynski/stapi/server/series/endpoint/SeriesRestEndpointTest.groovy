package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.rest.model.SeriesResponse
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader
import spock.lang.Specification

class SeriesRestEndpointTest extends Specification {

	private static final Integer PAGE_NUMBER = 1
	private static final Integer PAGE_SIZE = 10

	private static final String TITLE = 'TITLE'

	private SeriesRestReader seriesRestReaderMock

	private SeriesRestEndpoint seriesRestEndpoint

	def setup() {
		seriesRestReaderMock = Mock(SeriesRestReader)
		seriesRestEndpoint = new SeriesRestEndpoint(seriesRestReaderMock)
	}

	def "passes get call to SeriesRestReader"() {
		given:
		PageAwareBeanParams pageAwareBeanParams = Mock(PageAwareBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}
		SeriesResponse seriesResponse = Mock(SeriesResponse)

		when:
		SeriesResponse seriesResponseOutput = seriesRestEndpoint.getSeries(pageAwareBeanParams)

		then:
		1 * seriesRestReaderMock.read(_ as SeriesRestBeanParams) >> { SeriesRestBeanParams seriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			return seriesResponse
		}
		seriesResponseOutput == seriesResponse
	}

	def "passes post call to SeriesRestReader"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(title: TITLE)
		SeriesResponse seriesResponse = Mock(SeriesResponse)

		when:
		SeriesResponse seriesResponseOutput = seriesRestEndpoint.searchSeries(seriesRestBeanParams)

		then:
		1 * seriesRestReaderMock.read(seriesRestBeanParams as SeriesRestBeanParams) >> { SeriesRestBeanParams params ->
			assert params.title == TITLE
			return seriesResponse
		}
		seriesResponseOutput == seriesResponse
	}

}

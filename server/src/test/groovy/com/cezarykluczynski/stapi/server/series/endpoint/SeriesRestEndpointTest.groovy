package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.rest.model.Series
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesRestEndpointTest extends Specification {

	private static final String TITLE = 'TITLE'

	private SeriesRestReader seriesRestReaderMock

	private SeriesRestEndpoint seriesRestEndpoint

	def setup() {
		seriesRestReaderMock = Mock(SeriesRestReader)
		seriesRestEndpoint = new SeriesRestEndpoint(seriesRestReaderMock)
	}

	def "passes call to SeriesRestReader"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(title: TITLE)
		List<Series> seriesList = Lists.newArrayList()

		when:
		List<Series> seriesListOutput = seriesRestEndpoint.getSeries(seriesRestBeanParams)

		then:
		1 * seriesRestReaderMock.read(seriesRestBeanParams as SeriesRestBeanParams) >> { SeriesRestBeanParams params ->
			assert params.title == TITLE
			return seriesList
		}
		seriesListOutput == seriesList
	}

}

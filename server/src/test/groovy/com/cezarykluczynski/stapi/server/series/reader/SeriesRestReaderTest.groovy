package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.rest.model.Series as RESTSeries
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesRestReaderTest extends Specification {

	private SeriesQueryBuilder seriesQueryBuilderMock

	private SeriesRestMapper seriesRestMapperMock

	private SeriesRestReader seriesRestReader

	def setup() {
		seriesQueryBuilderMock = Mock(SeriesQueryBuilder)
		seriesRestMapperMock = Mock(SeriesRestMapper)
		seriesRestReader = new SeriesRestReader(seriesQueryBuilderMock, seriesRestMapperMock)
	}

	def "passed request to queryBuilder, then to mapper, and returns result"() {
		SeriesRestBeanParams seriesRestBeanParams = Mock(SeriesRestBeanParams)
		List<RESTSeries> restSeriesList = Lists.newArrayList()
		List<DBSeries> dbSeriesList = Lists.newArrayList()

		when:
		List<RESTSeries> restSeriesListOutput = seriesRestReader.read(seriesRestBeanParams)

		then:
		1 * seriesQueryBuilderMock.query(seriesRestBeanParams) >> dbSeriesList
		1 * seriesRestMapperMock.map(dbSeriesList) >> restSeriesList
		restSeriesListOutput == restSeriesList
	}

}

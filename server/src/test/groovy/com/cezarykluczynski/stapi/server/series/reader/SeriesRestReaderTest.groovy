package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.Series as RESTSeries
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesResponse
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesRestReaderTest extends Specification {

	private SeriesQuery seriesQueryBuilderMock

	private SeriesRestMapper seriesRestMapperMock

	private PageMapper pageMapperMock

	private SeriesRestReader seriesRestReader

	def setup() {
		seriesQueryBuilderMock = Mock(SeriesQuery)
		seriesRestMapperMock = Mock(SeriesRestMapper)
		pageMapperMock = Mock(PageMapper)
		seriesRestReader = new SeriesRestReader(seriesQueryBuilderMock, seriesRestMapperMock, pageMapperMock)
	}

	def "passed request to queryBuilder, then to mapper, and returns result"() {
		SeriesRestBeanParams seriesRestBeanParams = Mock(SeriesRestBeanParams)
		List<RESTSeries> restSeriesList = Lists.newArrayList()
		List<DBSeries> dbSeriesList = Lists.newArrayList()
		Page<DBSeries> dbSeriesPage = Mock(Page) {
			getContent() >> dbSeriesList
		}
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SeriesResponse seriesResponseOutput = seriesRestReader.read(seriesRestBeanParams)

		then:
		1 * seriesQueryBuilderMock.query(seriesRestBeanParams) >> dbSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbSeriesPage) >> responsePage
		1 * seriesRestMapperMock.map(dbSeriesList) >> restSeriesList
		seriesResponseOutput.series == restSeriesList
		seriesResponseOutput.page == responsePage
	}

}

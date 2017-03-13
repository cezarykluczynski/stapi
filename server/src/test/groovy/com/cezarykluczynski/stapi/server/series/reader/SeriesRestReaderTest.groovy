package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SeriesRestQuery seriesRestQueryBuilderMock

	private SeriesRestMapper seriesRestMapperMock

	private PageMapper pageMapperMock

	private SeriesRestReader seriesRestReader

	void setup() {
		seriesRestQueryBuilderMock = Mock(SeriesRestQuery)
		seriesRestMapperMock = Mock(SeriesRestMapper)
		pageMapperMock = Mock(PageMapper)
		seriesRestReader = new SeriesRestReader(seriesRestQueryBuilderMock, seriesRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = Mock(SeriesRestBeanParams)
		List<SeriesBase> restSeriesList = Lists.newArrayList(Mock(SeriesBase))
		List<Series> dbSeriesList = Lists.newArrayList(Mock(Series))
		Page<Series> dbSeriesPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SeriesBaseResponse seriesResponseOutput = seriesRestReader.readBase(seriesRestBeanParams)

		then:
		1 * seriesRestQueryBuilderMock.query(seriesRestBeanParams) >> dbSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbSeriesPage) >> responsePage
		1 * dbSeriesPage.content >> dbSeriesList
		1 * seriesRestMapperMock.mapBase(dbSeriesList) >> restSeriesList
		0 * _
		seriesResponseOutput.series == restSeriesList
		seriesResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		SeriesFull seriesFull = Mock(SeriesFull)
		Series series = Mock(Series)
		List<Series> dbSeriesList = Lists.newArrayList(series)
		Page<Series> dbSeriesPage = Mock(Page)

		when:
		SeriesFullResponse seriesResponseOutput = seriesRestReader.readFull(GUID)

		then:
		1 * seriesRestQueryBuilderMock.query(_ as SeriesRestBeanParams) >> { SeriesRestBeanParams seriesRestBeanParams ->
			assert seriesRestBeanParams.guid == GUID
			dbSeriesPage
		}
		1 * dbSeriesPage.content >> dbSeriesList
		1 * seriesRestMapperMock.mapFull(series) >> seriesFull
		0 * _
		seriesResponseOutput.series == seriesFull
	}

}

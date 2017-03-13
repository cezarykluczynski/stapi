package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SeriesSoapQuery seriesSoapQueryBuilderMock

	private SeriesSoapMapper seriesSoapMapperMock

	private PageMapper pageMapperMock

	private SeriesSoapReader seriesSoapReader

	void setup() {
		seriesSoapQueryBuilderMock = Mock(SeriesSoapQuery)
		seriesSoapMapperMock = Mock(SeriesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		seriesSoapReader = new SeriesSoapReader(seriesSoapQueryBuilderMock, seriesSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Series> dbSeriesList = Lists.newArrayList()
		Page<Series> dbSeriesPage = Mock(Page)
		List<SeriesBase> soapSeriesList = Lists.newArrayList(new SeriesBase(guid: GUID))
		SeriesBaseRequest seriesBaseRequest = Mock(SeriesBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SeriesBaseResponse seriesResponse = seriesSoapReader.readBase(seriesBaseRequest)

		then:
		1 * seriesSoapQueryBuilderMock.query(seriesBaseRequest) >> dbSeriesPage
		1 * dbSeriesPage.content >> dbSeriesList
		1 * pageMapperMock.fromPageToSoapResponsePage(dbSeriesPage) >> responsePage
		1 * seriesSoapMapperMock.mapBase(dbSeriesList) >> soapSeriesList
		seriesResponse.series[0].guid == GUID
		seriesResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SeriesFull seriesFull = new SeriesFull(guid: GUID)
		Series series = Mock(Series)
		Page<Series> seriesPage = Mock(Page)
		SeriesFullRequest seriesFullRequest = Mock(SeriesFullRequest)

		when:
		SeriesFullResponse seriesFullResponse = seriesSoapReader.readFull(seriesFullRequest)

		then:
		1 * seriesSoapQueryBuilderMock.query(seriesFullRequest) >> seriesPage
		1 * seriesPage.content >> Lists.newArrayList(series)
		1 * seriesSoapMapperMock.mapFull(series) >> seriesFull
		seriesFullResponse.series.guid == GUID
	}

}

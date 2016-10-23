package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.soap.ResponsePage
import com.cezarykluczynski.stapi.client.soap.Series as SOAPSeries
import com.cezarykluczynski.stapi.client.soap.SeriesRequest
import com.cezarykluczynski.stapi.client.soap.SeriesResponse
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesSoapReaderTest extends Specification {

	private static final Long ID = 1L

	private SeriesQueryBuilder seriesQueryBuilderMock

	private SeriesSoapMapper seriesSoapMapperMock

	private PageMapper pageMapperMock

	private SeriesSoapReader seriesSoapReader

	def setup() {
		seriesQueryBuilderMock = Mock(SeriesQueryBuilder)
		seriesSoapMapperMock = Mock(SeriesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		seriesSoapReader = new SeriesSoapReader(seriesQueryBuilderMock, seriesSoapMapperMock, pageMapperMock)
	}

	def "gets database entities and puts them into SeriesResponse"() {
		given:
		List<DBSeries> dbSeriesList = Lists.newArrayList()
		Page<DBSeries> dbSeriesPage = Mock(Page) {
			getContent() >> dbSeriesList
		}
		List<SOAPSeries> soapSeriesList = Lists.newArrayList(new SOAPSeries(id:  ID))
		SeriesRequest seriesRequest = Mock(SeriesRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SeriesResponse seriesResponse = seriesSoapReader.read(seriesRequest)

		then:
		1 * seriesQueryBuilderMock.query(seriesRequest) >> dbSeriesPage
		1 * pageMapperMock.toResponsePage(dbSeriesPage) >> responsePage
		1 * seriesSoapMapperMock.map(dbSeriesList) >> soapSeriesList
		seriesResponse.series[0].id == ID
		seriesResponse.page == responsePage
	}

}

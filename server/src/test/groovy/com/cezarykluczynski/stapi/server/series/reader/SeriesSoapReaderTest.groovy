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
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private SeriesSoapQuery seriesSoapQueryBuilderMock

	private SeriesBaseSoapMapper seriesBaseSoapMapperMock

	private SeriesFullSoapMapper seriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SeriesSoapReader seriesSoapReader

	void setup() {
		seriesSoapQueryBuilderMock = Mock(SeriesSoapQuery)
		seriesBaseSoapMapperMock = Mock(SeriesBaseSoapMapper)
		seriesFullSoapMapperMock = Mock(SeriesFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		seriesSoapReader = new SeriesSoapReader(seriesSoapQueryBuilderMock, seriesBaseSoapMapperMock, seriesFullSoapMapperMock,
				pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Series> seriesList = Lists.newArrayList()
		Page<Series> seriesPage = Mock(Page)
		List<SeriesBase> soapSeriesList = Lists.newArrayList(new SeriesBase(guid: GUID))
		SeriesBaseRequest seriesBaseRequest = Mock(SeriesBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		SeriesBaseResponse seriesResponse = seriesSoapReader.readBase(seriesBaseRequest)

		then:
		1 * seriesSoapQueryBuilderMock.query(seriesBaseRequest) >> seriesPage
		1 * seriesPage.content >> seriesList
		1 * pageMapperMock.fromPageToSoapResponsePage(seriesPage) >> responsePage
		1 * seriesBaseSoapMapperMock.mapBase(seriesList) >> soapSeriesList
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
		1 * seriesFullSoapMapperMock.mapFull(series) >> seriesFull
		seriesFullResponse.series.guid == GUID
	}

}

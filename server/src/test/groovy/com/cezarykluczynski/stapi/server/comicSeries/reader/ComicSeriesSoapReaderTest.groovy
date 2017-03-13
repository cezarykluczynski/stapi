package com.cezarykluczynski.stapi.server.comicSeries.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeries as SOAPComicSeries
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries as DBComicSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicSeriesSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicSeriesSoapQuery comicSeriesSoapQueryBuilderMock

	private ComicSeriesSoapMapper comicSeriesSoapMapperMock

	private PageMapper pageMapperMock

	private ComicSeriesSoapReader comicSeriesSoapReader

	void setup() {
		comicSeriesSoapQueryBuilderMock = Mock(ComicSeriesSoapQuery)
		comicSeriesSoapMapperMock = Mock(ComicSeriesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicSeriesSoapReader = new ComicSeriesSoapReader(comicSeriesSoapQueryBuilderMock, comicSeriesSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicSeriesResponse"() {
		given:
		List<DBComicSeries> dbComicSeriesList = Lists.newArrayList()
		Page<DBComicSeries> dbComicSeriesPage = Mock(Page)
		dbComicSeriesPage.content >> dbComicSeriesList
		List<SOAPComicSeries> soapComicSeriesList = Lists.newArrayList(new SOAPComicSeries(guid: GUID))
		ComicSeriesRequest comicSeriesRequest = Mock(ComicSeriesRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicSeriesResponse comicSeriesResponse = comicSeriesSoapReader.readBase(comicSeriesRequest)

		then:
		1 * comicSeriesSoapQueryBuilderMock.query(comicSeriesRequest) >> dbComicSeriesPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbComicSeriesPage) >> responsePage
		1 * comicSeriesSoapMapperMock.map(dbComicSeriesList) >> soapComicSeriesList
		comicSeriesResponse.comicSeries[0].guid == GUID
		comicSeriesResponse.page == responsePage
	}

}

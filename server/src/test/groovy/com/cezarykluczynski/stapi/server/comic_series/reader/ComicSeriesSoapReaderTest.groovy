package com.cezarykluczynski.stapi.server.comic_series.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.comic_series.query.ComicSeriesSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicSeriesSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicSeriesSoapQuery comicSeriesSoapQueryBuilderMock

	private ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapperMock

	private ComicSeriesFullSoapMapper comicSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicSeriesSoapReader comicSeriesSoapReader

	void setup() {
		comicSeriesSoapQueryBuilderMock = Mock()
		comicSeriesBaseSoapMapperMock = Mock()
		comicSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicSeriesSoapReader = new ComicSeriesSoapReader(comicSeriesSoapQueryBuilderMock, comicSeriesBaseSoapMapperMock,
				comicSeriesFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<ComicSeries> comicSeriesList = Lists.newArrayList()
		Page<ComicSeries> comicSeriesPage = Mock()
		List<ComicSeriesBase> soapComicSeriesList = Lists.newArrayList(new ComicSeriesBase(uid: UID))
		ComicSeriesBaseRequest comicSeriesBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesResponse = comicSeriesSoapReader.readBase(comicSeriesBaseRequest)

		then:
		1 * comicSeriesSoapQueryBuilderMock.query(comicSeriesBaseRequest) >> comicSeriesPage
		1 * comicSeriesPage.content >> comicSeriesList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicSeriesPage) >> responsePage
		1 * comicSeriesBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * comicSeriesBaseSoapMapperMock.mapBase(comicSeriesList) >> soapComicSeriesList
		0 * _
		comicSeriesResponse.comicSeries[0].uid == UID
		comicSeriesResponse.page == responsePage
		comicSeriesResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicSeriesFull comicSeriesFull = new ComicSeriesFull(uid: UID)
		ComicSeries comicSeries = Mock()
		Page<ComicSeries> comicSeriesPage = Mock()
		ComicSeriesFullRequest comicSeriesFullRequest = new ComicSeriesFullRequest(uid: UID)

		when:
		ComicSeriesFullResponse comicSeriesFullResponse = comicSeriesSoapReader.readFull(comicSeriesFullRequest)

		then:
		1 * comicSeriesSoapQueryBuilderMock.query(comicSeriesFullRequest) >> comicSeriesPage
		1 * comicSeriesPage.content >> Lists.newArrayList(comicSeries)
		1 * comicSeriesFullSoapMapperMock.mapFull(comicSeries) >> comicSeriesFull
		0 * _
		comicSeriesFullResponse.comicSeries.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ComicSeriesFullRequest comicSeriesFullRequest = Mock()

		when:
		comicSeriesSoapReader.readFull(comicSeriesFullRequest)

		then:
		thrown(MissingUIDException)
	}

}

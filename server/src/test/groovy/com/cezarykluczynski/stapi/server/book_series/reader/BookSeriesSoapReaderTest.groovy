package com.cezarykluczynski.stapi.server.book_series.reader

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.book_series.query.BookSeriesSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class BookSeriesSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private BookSeriesSoapQuery bookSeriesSoapQueryBuilderMock

	private BookSeriesBaseSoapMapper bookSeriesBaseSoapMapperMock

	private BookSeriesFullSoapMapper bookSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private BookSeriesSoapReader bookSeriesSoapReader

	void setup() {
		bookSeriesSoapQueryBuilderMock = Mock()
		bookSeriesBaseSoapMapperMock = Mock()
		bookSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		bookSeriesSoapReader = new BookSeriesSoapReader(bookSeriesSoapQueryBuilderMock, bookSeriesBaseSoapMapperMock, bookSeriesFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<BookSeries> bookSeriesList = Lists.newArrayList()
		Page<BookSeries> bookSeriesPage = Mock()
		List<BookSeriesBase> soapBookSeriesList = Lists.newArrayList(new BookSeriesBase(uid: UID))
		BookSeriesBaseRequest bookSeriesBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		BookSeriesBaseResponse bookSeriesResponse = bookSeriesSoapReader.readBase(bookSeriesBaseRequest)

		then:
		1 * bookSeriesSoapQueryBuilderMock.query(bookSeriesBaseRequest) >> bookSeriesPage
		1 * bookSeriesPage.content >> bookSeriesList
		1 * pageMapperMock.fromPageToSoapResponsePage(bookSeriesPage) >> responsePage
		1 * bookSeriesBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * bookSeriesBaseSoapMapperMock.mapBase(bookSeriesList) >> soapBookSeriesList
		0 * _
		bookSeriesResponse.bookSeries[0].uid == UID
		bookSeriesResponse.page == responsePage
		bookSeriesResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		BookSeriesFull bookSeriesFull = new BookSeriesFull(uid: UID)
		BookSeries bookSeries = Mock()
		Page<BookSeries> bookSeriesPage = Mock()
		BookSeriesFullRequest bookSeriesFullRequest = new BookSeriesFullRequest(uid: UID)

		when:
		BookSeriesFullResponse bookSeriesFullResponse = bookSeriesSoapReader.readFull(bookSeriesFullRequest)

		then:
		1 * bookSeriesSoapQueryBuilderMock.query(bookSeriesFullRequest) >> bookSeriesPage
		1 * bookSeriesPage.content >> Lists.newArrayList(bookSeries)
		1 * bookSeriesFullSoapMapperMock.mapFull(bookSeries) >> bookSeriesFull
		0 * _
		bookSeriesFullResponse.bookSeries.uid == UID
	}

	void "requires UID in full request"() {
		given:
		BookSeriesFullRequest bookSeriesFullRequest = Mock()

		when:
		bookSeriesSoapReader.readFull(bookSeriesFullRequest)

		then:
		thrown(MissingUIDException)
	}

}

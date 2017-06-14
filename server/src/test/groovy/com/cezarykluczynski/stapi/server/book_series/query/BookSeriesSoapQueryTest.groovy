package com.cezarykluczynski.stapi.server.book_series.query

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookSeriesSoapQueryTest extends Specification {

	private BookSeriesBaseSoapMapper bookSeriesBaseSoapMapperMock

	private BookSeriesFullSoapMapper bookSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private BookSeriesRepository bookSeriesRepositoryMock

	private BookSeriesSoapQuery bookSeriesSoapQuery

	void setup() {
		bookSeriesBaseSoapMapperMock = Mock()
		bookSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		bookSeriesRepositoryMock = Mock()
		bookSeriesSoapQuery = new BookSeriesSoapQuery(bookSeriesBaseSoapMapperMock, bookSeriesFullSoapMapperMock, pageMapperMock,
				bookSeriesRepositoryMock)
	}

	void "maps BookSeriesBaseRequest to BookSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		BookSeriesBaseRequest bookSeriesRequest = Mock()
		bookSeriesRequest.page >> requestPage
		BookSeriesRequestDTO bookSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookSeriesSoapQuery.query(bookSeriesRequest)

		then:
		1 * bookSeriesBaseSoapMapperMock.mapBase(bookSeriesRequest) >> bookSeriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * bookSeriesRepositoryMock.findMatching(bookSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps BookSeriesFullRequest to BookSeriesRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookSeriesFullRequest bookSeriesRequest = Mock()
		BookSeriesRequestDTO bookSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookSeriesSoapQuery.query(bookSeriesRequest)

		then:
		1 * bookSeriesFullSoapMapperMock.mapFull(bookSeriesRequest) >> bookSeriesRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * bookSeriesRepositoryMock.findMatching(bookSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

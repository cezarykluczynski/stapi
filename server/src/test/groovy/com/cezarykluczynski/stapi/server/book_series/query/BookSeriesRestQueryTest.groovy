package com.cezarykluczynski.stapi.server.book_series.query

import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookSeriesRestQueryTest extends Specification {

	private BookSeriesBaseRestMapper bookSeriesRestMapperMock

	private PageMapper pageMapperMock

	private BookSeriesRepository bookSeriesRepositoryMock

	private BookSeriesRestQuery bookSeriesRestQuery

	void setup() {
		bookSeriesRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookSeriesRepositoryMock = Mock()
		bookSeriesRestQuery = new BookSeriesRestQuery(bookSeriesRestMapperMock, pageMapperMock, bookSeriesRepositoryMock)
	}

	void "maps BookSeriesRestBeanParams to BookSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookSeriesRestBeanParams bookSeriesRestBeanParams = Mock()
		BookSeriesRequestDTO bookSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookSeriesRestQuery.query(bookSeriesRestBeanParams)

		then:
		1 * bookSeriesRestMapperMock.mapBase(bookSeriesRestBeanParams) >> bookSeriesRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(bookSeriesRestBeanParams) >> pageRequest
		1 * bookSeriesRepositoryMock.findMatching(bookSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

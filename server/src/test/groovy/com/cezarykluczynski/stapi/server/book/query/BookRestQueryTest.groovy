package com.cezarykluczynski.stapi.server.book.query

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.repository.BookRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookRestQueryTest extends Specification {

	private BookBaseRestMapper bookBaseRestMapperMock

	private PageMapper pageMapperMock

	private BookRepository bookRepositoryMock

	private BookRestQuery bookRestQuery

	void setup() {
		bookBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookRepositoryMock = Mock()
		bookRestQuery = new BookRestQuery(bookBaseRestMapperMock, pageMapperMock, bookRepositoryMock)
	}

	void "maps BookRestBeanParams to BookRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookRestBeanParams bookRestBeanParams = Mock()
		BookRequestDTO bookRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookRestQuery.query(bookRestBeanParams)

		then:
		1 * bookBaseRestMapperMock.mapBase(bookRestBeanParams) >> bookRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(bookRestBeanParams) >> pageRequest
		1 * bookRepositoryMock.findMatching(bookRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

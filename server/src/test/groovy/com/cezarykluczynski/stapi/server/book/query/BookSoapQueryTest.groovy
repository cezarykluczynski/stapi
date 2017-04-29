package com.cezarykluczynski.stapi.server.book.query

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO
import com.cezarykluczynski.stapi.model.book.repository.BookRepository
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookSoapQueryTest extends Specification {

	private BookBaseSoapMapper bookBaseSoapMapperMock

	private BookFullSoapMapper bookFullSoapMapperMock

	private PageMapper pageMapperMock

	private BookRepository bookRepositoryMock

	private BookSoapQuery bookSoapQuery

	void setup() {
		bookBaseSoapMapperMock = Mock()
		bookFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		bookRepositoryMock = Mock()
		bookSoapQuery = new BookSoapQuery(bookBaseSoapMapperMock, bookFullSoapMapperMock, pageMapperMock, bookRepositoryMock)
	}

	void "maps BookBaseRequest to BookRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		BookBaseRequest bookRequest = Mock()
		bookRequest.page >> requestPage
		BookRequestDTO bookRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookSoapQuery.query(bookRequest)

		then:
		1 * bookBaseSoapMapperMock.mapBase(bookRequest) >> bookRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * bookRepositoryMock.findMatching(bookRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps BookFullRequest to BookRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookFullRequest bookRequest = Mock()
		BookRequestDTO bookRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookSoapQuery.query(bookRequest)

		then:
		1 * bookFullSoapMapperMock.mapFull(bookRequest) >> bookRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * bookRepositoryMock.findMatching(bookRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

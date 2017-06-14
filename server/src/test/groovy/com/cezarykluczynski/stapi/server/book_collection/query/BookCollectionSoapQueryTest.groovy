package com.cezarykluczynski.stapi.server.book_collection.query

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookCollectionSoapQueryTest extends Specification {

	private BookCollectionBaseSoapMapper bookCollectionBaseSoapMapperMock

	private BookCollectionFullSoapMapper bookCollectionFullSoapMapperMock

	private PageMapper pageMapperMock

	private BookCollectionRepository bookCollectionRepositoryMock

	private BookCollectionSoapQuery bookCollectionSoapQuery

	void setup() {
		bookCollectionBaseSoapMapperMock = Mock()
		bookCollectionFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		bookCollectionRepositoryMock = Mock()
		bookCollectionSoapQuery = new BookCollectionSoapQuery(bookCollectionBaseSoapMapperMock, bookCollectionFullSoapMapperMock, pageMapperMock,
				bookCollectionRepositoryMock)
	}

	void "maps BookCollectionBaseRequest to BookCollectionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		BookCollectionBaseRequest bookCollectionRequest = Mock()
		bookCollectionRequest.page >> requestPage
		BookCollectionRequestDTO bookCollectionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookCollectionSoapQuery.query(bookCollectionRequest)

		then:
		1 * bookCollectionBaseSoapMapperMock.mapBase(bookCollectionRequest) >> bookCollectionRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * bookCollectionRepositoryMock.findMatching(bookCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps BookCollectionFullRequest to BookCollectionRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookCollectionFullRequest bookCollectionRequest = Mock()
		BookCollectionRequestDTO bookCollectionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookCollectionSoapQuery.query(bookCollectionRequest)

		then:
		1 * bookCollectionFullSoapMapperMock.mapFull(bookCollectionRequest) >> bookCollectionRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * bookCollectionRepositoryMock.findMatching(bookCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

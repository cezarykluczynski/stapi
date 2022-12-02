package com.cezarykluczynski.stapi.server.book_collection.query

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookCollectionRestQueryTest extends Specification {

	private BookCollectionBaseRestMapper bookCollectionBaseRestMapperMock

	private PageMapper pageMapperMock

	private BookCollectionRepository bookCollectionRepositoryMock

	private BookCollectionRestQuery bookCollectionRestQuery

	void setup() {
		bookCollectionBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookCollectionRepositoryMock = Mock()
		bookCollectionRestQuery = new BookCollectionRestQuery(bookCollectionBaseRestMapperMock, pageMapperMock, bookCollectionRepositoryMock)
	}

	void "maps BookCollectionRestBeanParams to BookCollectionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		BookCollectionRestBeanParams bookCollectionRestBeanParams = Mock()
		BookCollectionRequestDTO bookCollectionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = bookCollectionRestQuery.query(bookCollectionRestBeanParams)

		then:
		1 * bookCollectionBaseRestMapperMock.mapBase(bookCollectionRestBeanParams) >> bookCollectionRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(bookCollectionRestBeanParams) >> pageRequest
		1 * bookCollectionRepositoryMock.findMatching(bookCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

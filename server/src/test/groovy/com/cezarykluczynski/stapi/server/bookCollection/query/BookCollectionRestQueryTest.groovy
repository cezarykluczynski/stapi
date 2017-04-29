package com.cezarykluczynski.stapi.server.bookCollection.query

import com.cezarykluczynski.stapi.model.bookCollection.dto.BookCollectionRequestDTO
import com.cezarykluczynski.stapi.model.bookCollection.repository.BookCollectionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.bookCollection.dto.BookCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class BookCollectionRestQueryTest extends Specification {

	private BookCollectionBaseRestMapper bookCollectionRestMapperMock

	private PageMapper pageMapperMock

	private BookCollectionRepository bookCollectionRepositoryMock

	private BookCollectionRestQuery bookCollectionRestQuery

	void setup() {
		bookCollectionRestMapperMock = Mock()
		pageMapperMock = Mock()
		bookCollectionRepositoryMock = Mock()
		bookCollectionRestQuery = new BookCollectionRestQuery(bookCollectionRestMapperMock, pageMapperMock, bookCollectionRepositoryMock)
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
		1 * bookCollectionRestMapperMock.mapBase(bookCollectionRestBeanParams) >> bookCollectionRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(bookCollectionRestBeanParams) >> pageRequest
		1 * bookCollectionRepositoryMock.findMatching(bookCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

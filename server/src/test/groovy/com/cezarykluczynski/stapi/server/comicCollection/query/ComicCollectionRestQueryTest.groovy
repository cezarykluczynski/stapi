package com.cezarykluczynski.stapi.server.comicCollection.query

import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicCollectionRestQueryTest extends Specification {

	private ComicCollectionRestMapper comicCollectionRestMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionRepository comicCollectionRepositoryMock

	private ComicCollectionRestQuery comicCollectionRestQuery

	void setup() {
		comicCollectionRestMapperMock = Mock(ComicCollectionRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionRepositoryMock = Mock(ComicCollectionRepository)
		comicCollectionRestQuery = new ComicCollectionRestQuery(comicCollectionRestMapperMock, pageMapperMock,
				comicCollectionRepositoryMock)
	}

	void "maps ComicCollectionRestBeanParams to ComicCollectionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = Mock(ComicCollectionRestBeanParams) {

		}
		ComicCollectionRequestDTO comicCollectionRequestDTO = Mock(ComicCollectionRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicCollectionRestQuery.query(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestMapperMock.map(comicCollectionRestBeanParams) >> comicCollectionRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicCollectionRestBeanParams) >> pageRequest
		1 * comicCollectionRepositoryMock.findMatching(comicCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

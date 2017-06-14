package com.cezarykluczynski.stapi.server.comic_collection.query

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicCollectionRestQueryTest extends Specification {

	private ComicCollectionBaseRestMapper comicCollectionRestMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionRepository comicCollectionRepositoryMock

	private ComicCollectionRestQuery comicCollectionRestQuery

	void setup() {
		comicCollectionRestMapperMock = Mock()
		pageMapperMock = Mock()
		comicCollectionRepositoryMock = Mock()
		comicCollectionRestQuery = new ComicCollectionRestQuery(comicCollectionRestMapperMock, pageMapperMock, comicCollectionRepositoryMock)
	}

	void "maps ComicCollectionRestBeanParams to ComicCollectionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = Mock()
		ComicCollectionRequestDTO comicCollectionRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicCollectionRestQuery.query(comicCollectionRestBeanParams)

		then:
		1 * comicCollectionRestMapperMock.mapBase(comicCollectionRestBeanParams) >> comicCollectionRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicCollectionRestBeanParams) >> pageRequest
		1 * comicCollectionRepositoryMock.findMatching(comicCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

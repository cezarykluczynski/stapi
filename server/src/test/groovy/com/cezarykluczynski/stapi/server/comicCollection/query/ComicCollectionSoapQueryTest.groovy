package com.cezarykluczynski.stapi.server.comicCollection.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicCollectionSoapQueryTest extends Specification {

	private ComicCollectionSoapMapper comicCollectionSoapMapperMock

	private PageMapper pageMapperMock

	private ComicCollectionRepository comicCollectionRepositoryMock

	private ComicCollectionSoapQuery comicCollectionSoapQuery

	void setup() {
		comicCollectionSoapMapperMock = Mock(ComicCollectionSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicCollectionRepositoryMock = Mock(ComicCollectionRepository)
		comicCollectionSoapQuery = new ComicCollectionSoapQuery(comicCollectionSoapMapperMock, pageMapperMock, comicCollectionRepositoryMock)
	}

	void "maps ComicCollectionRequest to ComicCollectionRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		ComicCollectionRequest comicCollectionRequest = Mock(ComicCollectionRequest)
		comicCollectionRequest.page >> requestPage
		ComicCollectionRequestDTO comicCollectionRequestDTO = Mock(ComicCollectionRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicCollectionSoapQuery.query(comicCollectionRequest)

		then:
		1 * comicCollectionSoapMapperMock.map(comicCollectionRequest) >> comicCollectionRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicCollectionRepositoryMock.findMatching(comicCollectionRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

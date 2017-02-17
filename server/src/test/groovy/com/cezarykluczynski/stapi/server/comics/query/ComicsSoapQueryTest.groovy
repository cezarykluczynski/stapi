package com.cezarykluczynski.stapi.server.comics.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicsSoapQueryTest extends Specification {

	private ComicsSoapMapper comicsSoapMapperMock

	private PageMapper pageMapperMock

	private ComicsRepository comicsRepositoryMock

	private ComicsSoapQuery comicsSoapQuery

	void setup() {
		comicsSoapMapperMock = Mock(ComicsSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicsRepositoryMock = Mock(ComicsRepository)
		comicsSoapQuery = new ComicsSoapQuery(comicsSoapMapperMock, pageMapperMock, comicsRepositoryMock)
	}

	void "maps ComicsRequest to ComicsRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		ComicsRequest comicsRequest = Mock(ComicsRequest)
		comicsRequest.page >> requestPage
		ComicsRequestDTO comicsRequestDTO = Mock(ComicsRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicsSoapQuery.query(comicsRequest)

		then:
		1 * comicsSoapMapperMock.map(comicsRequest) >> comicsRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicsRepositoryMock.findMatching(comicsRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

package com.cezarykluczynski.stapi.server.comics.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicsSoapQueryTest extends Specification {

	private ComicsBaseSoapMapper comicsBaseSoapMapperMock

	private ComicsFullSoapMapper comicsFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicsRepository comicsRepositoryMock

	private ComicsSoapQuery comicsSoapQuery

	void setup() {
		comicsBaseSoapMapperMock = Mock()
		comicsFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		comicsRepositoryMock = Mock()
		comicsSoapQuery = new ComicsSoapQuery(comicsBaseSoapMapperMock, comicsFullSoapMapperMock, pageMapperMock, comicsRepositoryMock)
	}

	void "maps ComicsBaseRequest to ComicsRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		ComicsBaseRequest comicsRequest = Mock()
		comicsRequest.page >> requestPage
		ComicsRequestDTO comicsRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicsSoapQuery.query(comicsRequest)

		then:
		1 * comicsBaseSoapMapperMock.mapBase(comicsRequest) >> comicsRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicsRepositoryMock.findMatching(comicsRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ComicsFullRequest to ComicsRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicsFullRequest comicsRequest = Mock()
		ComicsRequestDTO comicsRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicsSoapQuery.query(comicsRequest)

		then:
		1 * comicsFullSoapMapperMock.mapFull(comicsRequest) >> comicsRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * comicsRepositoryMock.findMatching(comicsRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

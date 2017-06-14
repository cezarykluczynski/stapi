package com.cezarykluczynski.stapi.server.comic_strip.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicStripSoapQueryTest extends Specification {

	private ComicStripBaseSoapMapper comicStripBaseSoapMapperMock

	private ComicStripFullSoapMapper comicStripFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicStripRepository comicStripRepositoryMock

	private ComicStripSoapQuery comicStripSoapQuery

	void setup() {
		comicStripBaseSoapMapperMock = Mock()
		comicStripFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		comicStripRepositoryMock = Mock()
		comicStripSoapQuery = new ComicStripSoapQuery(comicStripBaseSoapMapperMock, comicStripFullSoapMapperMock, pageMapperMock,
				comicStripRepositoryMock)
	}

	void "maps ComicStripBaseRequest to ComicStripRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		ComicStripBaseRequest comicStripRequest = Mock()
		comicStripRequest.page >> requestPage
		ComicStripRequestDTO comicStripRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicStripSoapQuery.query(comicStripRequest)

		then:
		1 * comicStripBaseSoapMapperMock.mapBase(comicStripRequest) >> comicStripRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ComicStripFullRequest to ComicStripRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicStripFullRequest comicStripRequest = Mock()
		ComicStripRequestDTO comicStripRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicStripSoapQuery.query(comicStripRequest)

		then:
		1 * comicStripFullSoapMapperMock.mapFull(comicStripRequest) >> comicStripRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

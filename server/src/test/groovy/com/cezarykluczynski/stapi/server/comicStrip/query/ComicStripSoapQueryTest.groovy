package com.cezarykluczynski.stapi.server.comicStrip.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper
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
		comicStripBaseSoapMapperMock = Mock(ComicStripBaseSoapMapper)
		comicStripFullSoapMapperMock = Mock(ComicStripFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicStripRepositoryMock = Mock(ComicStripRepository)
		comicStripSoapQuery = new ComicStripSoapQuery(comicStripBaseSoapMapperMock, comicStripFullSoapMapperMock, pageMapperMock,
				comicStripRepositoryMock)
	}

	void "maps ComicStripBaseRequest to ComicStripRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		ComicStripBaseRequest comicStripRequest = Mock(ComicStripBaseRequest)
		comicStripRequest.page >> requestPage
		ComicStripRequestDTO comicStripRequestDTO = Mock(ComicStripRequestDTO)
		Page page = Mock(Page)

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
		PageRequest pageRequest = Mock(PageRequest)
		ComicStripFullRequest comicStripRequest = Mock(ComicStripFullRequest)
		ComicStripRequestDTO comicStripRequestDTO = Mock(ComicStripRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicStripSoapQuery.query(comicStripRequest)

		then:
		1 * comicStripFullSoapMapperMock.mapFull(comicStripRequest) >> comicStripRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

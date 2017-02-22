package com.cezarykluczynski.stapi.server.comicStrip.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicStripSoapQueryTest extends Specification {

	private ComicStripSoapMapper comicStripSoapMapperMock

	private PageMapper pageMapperMock

	private ComicStripRepository comicStripRepositoryMock

	private ComicStripSoapQuery comicStripSoapQuery

	void setup() {
		comicStripSoapMapperMock = Mock(ComicStripSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicStripRepositoryMock = Mock(ComicStripRepository)
		comicStripSoapQuery = new ComicStripSoapQuery(comicStripSoapMapperMock, pageMapperMock, comicStripRepositoryMock)
	}

	void "maps ComicStripRequest to ComicStripRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		ComicStripRequest comicStripRequest = Mock(ComicStripRequest)
		comicStripRequest.page >> requestPage
		ComicStripRequestDTO comicStripRequestDTO = Mock(ComicStripRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicStripSoapQuery.query(comicStripRequest)

		then:
		1 * comicStripSoapMapperMock.map(comicStripRequest) >> comicStripRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

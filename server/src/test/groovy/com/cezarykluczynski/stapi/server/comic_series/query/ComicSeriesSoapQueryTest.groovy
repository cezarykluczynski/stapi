package com.cezarykluczynski.stapi.server.comic_series.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicSeriesSoapQueryTest extends Specification {

	private ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapperMock

	private ComicSeriesFullSoapMapper comicSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private ComicSeriesSoapQuery comicSeriesSoapQuery

	void setup() {
		comicSeriesBaseSoapMapperMock = Mock()
		comicSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		comicSeriesRepositoryMock = Mock()
		comicSeriesSoapQuery = new ComicSeriesSoapQuery(comicSeriesBaseSoapMapperMock, comicSeriesFullSoapMapperMock, pageMapperMock,
				comicSeriesRepositoryMock)
	}

	void "maps ComicSeriesBaseRequest to ComicSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		ComicSeriesBaseRequest comicSeriesRequest = Mock()
		comicSeriesRequest.page >> requestPage
		ComicSeriesRequestDTO comicSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicSeriesSoapQuery.query(comicSeriesRequest)

		then:
		1 * comicSeriesBaseSoapMapperMock.mapBase(comicSeriesRequest) >> comicSeriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicSeriesRepositoryMock.findMatching(comicSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ComicSeriesFullRequest to ComicSeriesRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicSeriesFullRequest comicSeriesRequest = Mock()
		ComicSeriesRequestDTO comicSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicSeriesSoapQuery.query(comicSeriesRequest)

		then:
		1 * comicSeriesFullSoapMapperMock.mapFull(comicSeriesRequest) >> comicSeriesRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * comicSeriesRepositoryMock.findMatching(comicSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

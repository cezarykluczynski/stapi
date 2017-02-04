package com.cezarykluczynski.stapi.server.comicSeries.query

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicSeriesSoapQueryTest extends Specification {

	private ComicSeriesSoapMapper comicSeriesSoapMapperMock

	private PageMapper pageMapperMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private ComicSeriesSoapQuery comicSeriesSoapQuery

	void setup() {
		comicSeriesSoapMapperMock = Mock(ComicSeriesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicSeriesRepositoryMock = Mock(ComicSeriesRepository)
		comicSeriesSoapQuery = new ComicSeriesSoapQuery(comicSeriesSoapMapperMock, pageMapperMock,
				comicSeriesRepositoryMock)
	}

	void "maps ComicSeriesRequest to ComicSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		ComicSeriesRequest comicSeriesRequest = Mock(ComicSeriesRequest)
		comicSeriesRequest.page >> requestPage
		ComicSeriesRequestDTO comicSeriesRequestDTO = Mock(ComicSeriesRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicSeriesSoapQuery.query(comicSeriesRequest)

		then:
		1 * comicSeriesSoapMapperMock.map(comicSeriesRequest) >> comicSeriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * comicSeriesRepositoryMock.findMatching(comicSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}

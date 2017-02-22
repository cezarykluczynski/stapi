package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeriesSoapQueryTest extends Specification {

	private SeriesSoapMapper seriesSoapMapperMock

	private PageMapper pageMapperMock

	private SeriesRepository seriesRepositoryMock

	private SeriesSoapQuery seriesSoapQuery

	void setup() {
		seriesSoapMapperMock = Mock(SeriesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		seriesRepositoryMock = Mock(SeriesRepository)
		seriesSoapQuery = new SeriesSoapQuery(seriesSoapMapperMock, pageMapperMock, seriesRepositoryMock)
	}

	void "maps SeriesRequest to SeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		SeriesRequest seriesRequest = Mock(SeriesRequest)
		seriesRequest.page >> requestPage
		SeriesRequestDTO seriesRequestDTO = Mock(SeriesRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = seriesSoapQuery.query(seriesRequest)

		then:
		1 * seriesSoapMapperMock.map(seriesRequest) >> seriesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * seriesRepositoryMock.findMatching(seriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
